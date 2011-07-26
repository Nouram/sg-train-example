package com.skynet.spms.client.gui.supplierSupport.procurementOrder.transferOrder;

import com.skynet.spms.client.gui.base.Utils;
import com.skynet.spms.client.widgets.grid.FilterListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

/**
 * 主订单列表
 * @author Tony FANG
 *
 */
public class TransferOrderListGrid extends FilterListGrid {

	public TransferOrderListGrid() {

	}

	public void drawGrid() {
		this.setShowRowNumbers(false);
		ListGridField field1 = new ListGridField("field1", "留言");
		Utils.setMessageFieldWidth(field1);
		field1.setHidden(true);
		
		//调拨单编号
		ListGridField field2 = new ListGridField("transferSheetNumber", "调拨单编号");
		
		//优先级
		ListGridField field3 = new ListGridField("m_Priority");

		//调拨来源企业
		ListGridField field4 = new ListGridField("transferFrom.code");
		
		//运代商
		ListGridField field9 = new ListGridField("m_CarrierName.code");

//		ListGridField field5 = new ListGridField("m_Payment", "支付方式");

		ListGridField field6 = new ListGridField("m_TransportationCode", "运输方式");

		ListGridField field10 = new ListGridField("m_BussinessPublishStatusEntity.m_PublishStatus", "发布状态");
		
		ListGridField field7 = new ListGridField("m_BussinessStatusEntity.status", "业务状态");
		ListGridField field8 = new ListGridField("remarkText", "备注");

		field1.setCanFilter(true);
		field2.setCanFilter(true);
		field3.setCanFilter(true);
		field4.setCanFilter(true);
//		field5.setCanFilter(true);
		field6.setCanFilter(true);
		field7.setCanFilter(true);
		field8.setCanFilter(true);
		field9.setCanFilter(true);
		field10.setCanFilter(true);
		
		setFields(field1, field2, field3, field4,field9, field6, field10,field7,
				field8);


	}


}
