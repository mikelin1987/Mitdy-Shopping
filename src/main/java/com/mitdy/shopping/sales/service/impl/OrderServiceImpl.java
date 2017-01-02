package com.mitdy.shopping.sales.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mitdy.core.domain.Auditer;
import com.mitdy.core.util.StringHelper;
import com.mitdy.shopping.member.domain.Member;
import com.mitdy.shopping.member.persistence.MemberDao;
import com.mitdy.shopping.sales.domain.GoodsPricing;
import com.mitdy.shopping.sales.domain.SalesActivityItem;
import com.mitdy.shopping.sales.domain.SalesOrder;
import com.mitdy.shopping.sales.domain.SalesOrderItem;
import com.mitdy.shopping.sales.dto.CreateActivityOrderDTO;
import com.mitdy.shopping.sales.persistence.SalesActivityItemDao;
import com.mitdy.shopping.sales.persistence.SalesOrderDao;
import com.mitdy.shopping.sales.persistence.SalesOrderItemDao;
import com.mitdy.shopping.sales.service.OrderService;

@Transactional
@Service("orderService")
public class OrderServiceImpl implements OrderService {

	@Autowired
	private SalesOrderDao salesOrderDao;

	@Autowired
	private SalesActivityItemDao salesActivityItemDao;

	@Autowired
	private MemberDao memberDao;

	@Autowired
	private SalesOrderItemDao salesOrderItemDao;

	private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	
	@Override
	public void createActivityOrder(CreateActivityOrderDTO orderDTO) {
		Member member = memberDao.findById(orderDTO.getMemberId());
		if (member == null) {
			throw new IllegalArgumentException("Could not find the member with id'" + orderDTO.getMemberId() + "'");
		}

		SalesActivityItem salesActivityItem = salesActivityItemDao.findById(orderDTO.getActivityItemId());
		if (salesActivityItem == null) {
			throw new IllegalArgumentException(
					"Could not find the salesActivityItem with id'" + orderDTO.getActivityItemId() + "'");
		}
		
		if (salesActivityItem.isSellOut()) {
			throw new IllegalStateException("The goods is sell out");
		}

		if (orderDTO.getQuantity() < 0) {
			throw new IllegalArgumentException("Invalid quantity of value '0'");
		}

		if (orderDTO.getQuantity() > salesActivityItem.getLimitationCountPerMember()) {
			throw new IllegalArgumentException("Invalid quantity of '" + orderDTO.getQuantity() + "'");
		}
		
		long countOrderItemByMember = salesOrderItemDao.countOrderItemByMember(orderDTO.getActivityItemId(), orderDTO.getMemberId());
		if (countOrderItemByMember + orderDTO.getQuantity() > salesActivityItem.getLimitationCountPerMember()) {
			throw new IllegalArgumentException("Invalid quantity of '" + orderDTO.getQuantity() + "'");
		}

		salesActivityItem.setSellCount(salesActivityItem.getSellCount() - orderDTO.getQuantity());
		Auditer.audit(salesActivityItem, null);
		salesActivityItemDao.save(salesActivityItem);
		
		GoodsPricing goodsPricing = salesActivityItem.getGoodsPricing();
		
		String orderNo = formatter.format(new Date()) + StringHelper.getRandomString(4);
		SalesOrder salesOrder = new SalesOrder(orderNo, orderDTO.getMemberId());
		salesOrder.setPayerName(orderDTO.getPayerName());
		salesOrder.setContactNo(orderDTO.getContactNo());
		salesOrder.setPaymentType(orderDTO.getPaymentType());
		salesOrder.setDeliverAmount(new BigDecimal(0));
		salesOrder.setOrderAmount(goodsPricing.getUnitPrice().multiply(salesActivityItem.getDiscountPercentage()).multiply(new BigDecimal(orderDTO.getQuantity())).setScale(2, RoundingMode.HALF_UP));
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
		
		
	}

}
