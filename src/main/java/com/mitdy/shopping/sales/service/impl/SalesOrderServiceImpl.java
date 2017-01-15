package com.mitdy.shopping.sales.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mitdy.core.domain.Auditer;
import com.mitdy.core.util.StringHelper;
import com.mitdy.shopping.member.service.MemberService;
import com.mitdy.shopping.sales.domain.GoodsPricing;
import com.mitdy.shopping.sales.domain.SalesActivityItem;
import com.mitdy.shopping.sales.domain.SalesOrder;
import com.mitdy.shopping.sales.domain.SalesOrderItem;
import com.mitdy.shopping.sales.dto.CreateActivityOrderDTO;
import com.mitdy.shopping.sales.dto.GoodsInfoDTO;
import com.mitdy.shopping.sales.dto.SalesOrderDTO;
import com.mitdy.shopping.sales.dto.SalesOrderItemDTO;
import com.mitdy.shopping.sales.enumeration.SalesOrderStatus;
import com.mitdy.shopping.sales.mapper.SalesOrderMapper;
import com.mitdy.shopping.sales.persistence.SalesOrderDao;
import com.mitdy.shopping.sales.persistence.SalesOrderItemDao;
import com.mitdy.shopping.sales.service.SalesActivityService;
import com.mitdy.shopping.sales.service.SalesOrderService;

@Transactional
@Service("salesOrderService")
public class SalesOrderServiceImpl implements SalesOrderService {

    @Autowired
    private SalesOrderDao salesOrderDao;

    @Autowired
    private MemberService memberService;

    @Autowired
    private SalesOrderItemDao salesOrderItemDao;

    @Autowired
    private SalesActivityService salesActivityService;
    
    @Autowired
    private SalesOrderMapper salesOrderMapper;

    @Autowired
    private DataSource dataSource;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    @Override
    public void createActivityOrderByHibernate(CreateActivityOrderDTO orderDTO) {

        if (orderDTO.getQuantity() < 0) {
            throw new IllegalArgumentException("Invalid quantity of value '0'");
        }

        long memberCount = memberService.getCountById(orderDTO.getMemberId());
        if (memberCount == 0) {
            throw new IllegalArgumentException("Could not find the member with id'" + orderDTO.getMemberId() + "'");
        }

        // SalesActivityItem salesActivityItem =
        // salesActivityService.findSalesActivityItemById(orderDTO.getActivityItemId());
        // if (salesActivityItem == null) {
        // throw new IllegalArgumentException(
        // "Could not find the salesActivityItem with id'" +
        // orderDTO.getActivityItemId() + "'");
        // }
        //
        // if (salesActivityItem.isSellOut()) {
        // throw new IllegalStateException("The goods is sell out");
        // }
        //
        // if (orderDTO.getQuantity() >
        // salesActivityItem.getLimitationCountPerMember()) {
        // throw new IllegalArgumentException("Invalid quantity of '" +
        // orderDTO.getQuantity() + "'");
        // }
        //
        // long countOrderItemByMember =
        // salesOrderItemDao.countOrderItemByMember(orderDTO.getActivityItemId(),
        // orderDTO.getMemberId());
        // if (countOrderItemByMember + orderDTO.getQuantity() >
        // salesActivityItem.getLimitationCountPerMember()) {
        // throw new IllegalArgumentException("Invalid quantity of '" +
        // orderDTO.getQuantity() + "'");
        // }

        System.out.println("before update item, " +Thread.currentThread().getName());

        int updateCount = salesActivityService.increaseActivityItemSellCount(orderDTO.getActivityItemId(), orderDTO.getQuantity());
        if (updateCount > 0) {

            SalesActivityItem salesActivityItem = salesActivityService.findSalesActivityItemById(orderDTO.getActivityItemId());

            System.out.println(
                    "after update item, " +Thread.currentThread().getName() + ", salesActivityItem: " + salesActivityItem.getSellCount());

            GoodsPricing goodsPricing = salesActivityItem.getGoodsPricing();

            String orderNo = formatter.format(new Date()) + StringHelper.getRandomString(4);
            SalesOrder salesOrder = new SalesOrder(orderNo, orderDTO.getMemberId());
            salesOrder.setPayerName(orderDTO.getPayerName());
            salesOrder.setContactNo(orderDTO.getContactNo());
            salesOrder.setPaymentType(orderDTO.getPaymentType());
            salesOrder.setDeliverAmount(new BigDecimal(0));
            salesOrder.setOrderAmount(goodsPricing.getUnitPrice().multiply(salesActivityItem.getDiscountPercentage()).multiply(new BigDecimal(orderDTO.getQuantity())).setScale(2,
                    RoundingMode.HALF_UP));
            salesOrder.setDiscountAmount(new BigDecimal(0.0));
            salesOrder.setActualAmount(salesOrder.getOrderAmount().add(salesOrder.getDeliverAmount()).setScale(2, RoundingMode.HALF_UP));
            salesOrder.setSubmitTime(new Date());
            Auditer.audit(salesOrder, null);

            salesOrder = salesOrderDao.save(salesOrder);

            SalesOrderItem orderItem = new SalesOrderItem(salesOrder);
            orderItem.setSalesActivityItem(salesActivityItem);
            orderItem.setGoodsId(goodsPricing.getGoods().getId());
            orderItem.setGoodsName(goodsPricing.getGoods().getGoodsName());
            orderItem.setGoodsDesc(goodsPricing.getGoods().getGoodsDesc());
            orderItem.setUnitPrice(goodsPricing.getUnitPrice());
            orderItem.setActualUnitPrice(goodsPricing.getUnitPrice().multiply(salesActivityItem.getDiscountPercentage().setScale(2, RoundingMode.HALF_UP)));
            orderItem.setQuantity(new BigDecimal(orderDTO.getQuantity()).setScale(2, RoundingMode.HALF_UP));
            orderItem.setTotalAmount(orderItem.getActualUnitPrice().multiply(orderItem.getQuantity()).setScale(2, RoundingMode.HALF_UP));

            Auditer.audit(orderItem, null);

            salesOrderItemDao.save(orderItem);
        } else {
            System.out.println("after update item occurred exception, " +Thread.currentThread().getName());

            throw new IllegalStateException("The goods is sell out!");
        }

    }

    @Override
    public void createActivityOrderByNativeSQL(CreateActivityOrderDTO orderDTO) throws Exception {

        if (orderDTO.getQuantity() < 0) {
            throw new IllegalArgumentException("Invalid quantity of value '0'");
        }

        Connection conn = dataSource.getConnection();
        
        conn.setAutoCommit(false);
        PreparedStatement preStmt = conn.prepareStatement("select count(id) from member_member where id = ?");
        preStmt.setLong(1, orderDTO.getMemberId());
        ResultSet rs = preStmt.executeQuery();

        if (rs.next()) {
            long memeberCount = rs.getLong(1);

            if (memeberCount == 0) {
                throw new IllegalArgumentException("Could not find the member with id'" + orderDTO.getMemberId() + "'");
            }
        }

        System.out.println("before update item, " +Thread.currentThread().getName() + ", conn: " + conn);

        preStmt = conn.prepareStatement("update sales_sales_activity_item set sell_count = sell_count + ? where id = ? and sell_count + ? <= seconds_kill_count");
        preStmt.setInt(1, orderDTO.getQuantity());
        preStmt.setLong(2, orderDTO.getActivityItemId());
        preStmt.setInt(3, orderDTO.getQuantity());
        int updateCount = preStmt.executeUpdate();

        boolean shouldCommit = false;
        if (updateCount > 0) {
            // unitprice, goods_id, goods_name, goods_desc,

            try {
                preStmt = conn.prepareStatement(
                        "select g.id as goodsId, g.goods_name as goodsName, g.goods_desc as goodsDesc, gp.unit_price as goodsUnitPrice, ai.discount_percentage as discountPercentage from sales_sales_activity_item ai left outer join sales_goods_pricing gp on gp.id = ai.goods_pricing_id left outer join goods_goods g on g.id = gp.goods_id where ai.id = ?");
                preStmt.setLong(1, orderDTO.getActivityItemId());
                rs = preStmt.executeQuery();
                if (rs.next()) {
                    Long goodsId = rs.getLong(1);
                    String goodsName = rs.getString(2);
                    String goodsDesc = rs.getString(3);
                    BigDecimal goodsUnitPrice = rs.getBigDecimal(4);
                    BigDecimal discountPercentage = rs.getBigDecimal(5);

                    String orderNo = formatter.format(new Date()) + StringHelper.getRandomString(4);

                    preStmt = conn.prepareStatement(
                            "insert into sales_sales_order(create_time,create_user,last_update_time,last_update_user,actual_amount,contact_no,deliver_amount,discount_amount,member_id,order_amount,order_no,order_status,payer_name,payment_type,submit_time) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                            Statement.RETURN_GENERATED_KEYS);

                    BigDecimal orderAmount = goodsUnitPrice.multiply(discountPercentage).multiply(new BigDecimal(orderDTO.getQuantity())).setScale(2, RoundingMode.HALF_UP);
                    BigDecimal deliverAmount = new BigDecimal(0);

                    java.sql.Date createTime = new java.sql.Date(System.currentTimeMillis());
                    preStmt.setDate(1, createTime);
                    preStmt.setString(2, Auditer.SYSTEM_RESERVED_USER_ID);
                    preStmt.setDate(3, createTime);
                    preStmt.setString(4, Auditer.SYSTEM_RESERVED_USER_ID);
                    preStmt.setBigDecimal(5, orderAmount.add(deliverAmount).setScale(2, RoundingMode.HALF_UP));

                    preStmt.setString(6, orderDTO.getContactNo());
                    preStmt.setBigDecimal(7, deliverAmount);
                    preStmt.setBigDecimal(8, new BigDecimal(0));
                    preStmt.setLong(9, orderDTO.getMemberId());
                    preStmt.setBigDecimal(10, orderAmount);

                    preStmt.setString(11, orderNo);
                    preStmt.setString(12, SalesOrderStatus.PENDING.toString());
                    preStmt.setString(13, orderDTO.getPayerName());
                    preStmt.setString(14, orderDTO.getPaymentType());
                    preStmt.setDate(15, createTime);
                    int executeUpdate = preStmt.executeUpdate();

                    rs = preStmt.getGeneratedKeys();
                    if (rs.next()) {
                        Long generatedOrderId = rs.getLong(1);

                        BigDecimal actualUnitPrice = goodsUnitPrice.multiply(discountPercentage.setScale(2, RoundingMode.HALF_UP));
                        BigDecimal quantity = new BigDecimal(orderDTO.getQuantity());
                        BigDecimal totalAmount = actualUnitPrice.multiply(quantity).setScale(2, RoundingMode.HALF_UP);

                        preStmt = conn.prepareStatement(
                                "insert into sales_sales_order_item(create_time,create_user,last_update_time,last_update_user,actual_unit_price,goods_desc,goods_id,goods_name,quantity,total_amount,unit_price,order_id,sales_activity_item_id) values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
                        preStmt.setDate(1, createTime);
                        preStmt.setString(2, Auditer.SYSTEM_RESERVED_USER_ID);
                        preStmt.setDate(3, createTime);
                        preStmt.setString(4, Auditer.SYSTEM_RESERVED_USER_ID);
                        preStmt.setBigDecimal(5, actualUnitPrice);

                        preStmt.setString(6, goodsDesc);
                        preStmt.setLong(7, goodsId);
                        preStmt.setString(8, goodsName);
                        preStmt.setBigDecimal(9, quantity);
                        preStmt.setBigDecimal(10, totalAmount);

                        preStmt.setBigDecimal(11, goodsUnitPrice);
                        preStmt.setLong(12, generatedOrderId);
                        preStmt.setLong(13, orderDTO.getActivityItemId());

                        executeUpdate = preStmt.executeUpdate();

                        shouldCommit = true;

                    }

                }
                System.out.println(Thread.currentThread().getName() + ", shouldCommit: " + shouldCommit);
                
                if (shouldCommit) {
                    System.out.println("commit ");
                    
                    conn.commit();
                    
                    conn.close();
                    conn = null; 
                    
                    preStmt.close();
                    preStmt = null;
                    
                    rs.close();
                    rs = null;
                            
                } else {
                    System.out.println("rollback ");
                    conn.rollback();
                    
                    conn.close();
                    conn = null; 
                    
                    preStmt.close();
                    preStmt = null;
                    
                    rs.close();
                    rs = null;
                } 
            } catch (Exception e) {
                System.out.println(Thread.currentThread().getName() + ", e: " + e);
                
                conn.rollback();
                
                conn.close();
                conn = null; 
                
                preStmt.close();
                preStmt = null;
                
                rs.close();
                rs = null;
                throw new IllegalStateException(e);
            }

        } else {
            System.out.println("sell out " +Thread.currentThread().getName());
            conn.commit();
            
            conn.close();
            conn = null; 
            
            preStmt.close();
            preStmt = null;
            
            rs.close();
            rs = null;
            throw new IllegalStateException("The goods is sell out!");
        }
        
    }

    @Override
    public void createActivityOrderByMyBatis(CreateActivityOrderDTO orderDTO) {
        if (orderDTO.getQuantity() < 0) {
            throw new IllegalArgumentException("Invalid quantity of value '0'");
        }
        
        long memberCount = memberService.getCountById(orderDTO.getMemberId());
        if (memberCount == 0) {
            throw new IllegalArgumentException("Could not find the member with id'" + orderDTO.getMemberId() + "'");
        }
        
        System.out.println("before update item, " +Thread.currentThread().getName());
        
        int updateCount = salesActivityService.increaseActivityItemSellCount(orderDTO.getActivityItemId(), orderDTO.getQuantity());
        
        if (updateCount > 0) {
            
            // get goods info
            GoodsInfoDTO goodsInfo = salesActivityService.findGoodsInfoMapByItemId(orderDTO.getActivityItemId());
//            Long goodsId = (Long) goodsInfo.get("GOODS_ID");
//            String goodsName = (String) goodsInfo.get("GOOD_NAME");
//            String goodsDesc = (String) goodsInfo.get("GOODS_DESC");
//            BigDecimal goodsUnitPrice = (BigDecimal) goodsInfo.get("GOODS_UNIT_PRICE");
//            BigDecimal discountPercentage = (BigDecimal) goodsInfo.get("DISCOUNT_PERCENTAGE");
            
            Long goodsId = goodsInfo.getGoodsId();
            String goodsName = goodsInfo.getGoodsName();
            String goodsDesc = goodsInfo.getGoodsDesc();
            BigDecimal goodsUnitPrice = goodsInfo.getGoodsUnitPrice();
            BigDecimal discountPercentage = goodsInfo.getDiscountPercentage();

            String orderNo = formatter.format(new Date()) + StringHelper.getRandomString(4);
            
            BigDecimal orderAmount = goodsUnitPrice.multiply(discountPercentage).multiply(new BigDecimal(orderDTO.getQuantity())).setScale(2, RoundingMode.HALF_UP);
            BigDecimal deliverAmount = new BigDecimal(0);
            java.sql.Date createTime = new java.sql.Date(System.currentTimeMillis());
            
            // create sales order
            SalesOrderDTO order = new SalesOrderDTO();
            order.setOrderNo(orderNo);
            order.setMemberId(orderDTO.getMemberId());
            order.setPayerName(orderDTO.getPayerName());
            order.setContactNo(orderDTO.getContactNo());
            order.setPaymentType(orderDTO.getPaymentType());
            order.setOrderStatus(SalesOrderStatus.PENDING);
            order.setDeliverAmount(deliverAmount);
            order.setOrderAmount(orderAmount);
            order.setDiscountAmount(new BigDecimal(0.0));
            order.setActualAmount(order.getOrderAmount().add(order.getDeliverAmount()).setScale(2, RoundingMode.HALF_UP));
            order.setSubmitTime(createTime);
            Auditer.audit(order, null);
            salesOrderMapper.insertSalesOrder(order);
            
            
            BigDecimal actualUnitPrice = goodsUnitPrice.multiply(discountPercentage.setScale(2, RoundingMode.HALF_UP));
            BigDecimal quantity = new BigDecimal(orderDTO.getQuantity());
            BigDecimal totalAmount = actualUnitPrice.multiply(quantity).setScale(2, RoundingMode.HALF_UP);
            
            // create sales order item
            SalesOrderItemDTO orderItem = new SalesOrderItemDTO();
            orderItem.setOrderId(order.getId());
            orderItem.setSalesActivityItemId(orderDTO.getActivityItemId());
            orderItem.setGoodsId(goodsId);
            orderItem.setGoodsName(goodsName);
            orderItem.setGoodsDesc(goodsDesc);
            orderItem.setUnitPrice(goodsUnitPrice);
            orderItem.setActualUnitPrice(actualUnitPrice);
            orderItem.setQuantity(quantity);
            orderItem.setTotalAmount(totalAmount);
            Auditer.audit(orderItem, null);
            salesOrderMapper.insertSalesOrderItem(orderItem);
            
            System.out.println(
                    "after update by " +Thread.currentThread().getName());
            
        } else {
            System.out.println("after update item occurred exception, " +Thread.currentThread().getName());

            throw new IllegalStateException("The goods is sell out!");
        }
        
        
    }

}
