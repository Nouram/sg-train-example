package com.skynet.spms.client.gui.commonOrder.delivery.ui;

import com.skynet.spms.client.entity.DataInfo;
import com.skynet.spms.client.feature.data.DataSourceTool;
import com.skynet.spms.client.feature.data.DataSourceTool.PostDataSourceInit;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * 发货指令单
 * 
 * @author Tony FANG
 * 
 */
public class DeliveryOrderPanel extends VLayout {

	private static DeliveryOrderToolStrip toolStripPanel;
	private static DeliveryOrderListGrid listGrid;
	private static DeliveryOrderItemListGrid itemListGrid;
	private String modName;
	
	public DeliveryOrderPanel(){}

	public DeliveryOrderPanel(String businessType,String modName) {
		if(modName==null||"".equals(modName)){
			modName="supplierSupport.repairClaim.repairDeliveryOrder";
		}
		this.modName=modName;
		setWidth("99%");
		setHeight("99%");
		boolean topIntro = isTopIntro();
		Layout layout = topIntro ? new VLayout() : new HLayout();
		layout.setWidth100();
		layout.setMargin(1);
		layout.setMembersMargin(2);
		Canvas panel = getViewPanel(businessType);
		HLayout wrapper = new HLayout();
		wrapper.setWidth100();
		wrapper.addMember(panel);
		layout.addMember(wrapper);
		addMember(layout);
	}

	public Canvas getViewPanel(final String businessType) {
		// 订单列表
		listGrid = new DeliveryOrderListGrid();
		final DataSourceTool tool = new DataSourceTool();
		tool.onCreateDataSource(
				"supplierSupport.repairClaim.repairDeliveryOrder",
				"PickupDeliveryOrder_datasource", new PostDataSourceInit() {
					public void doPostOper(DataSource dataSource,
							DataInfo dataInfo) {
						listGrid.setDataSource(dataSource);
						Criteria criteria = new Criteria();
						criteria.addCriteria("type", "2");
						criteria.addCriteria("businessType", businessType);
						listGrid.fetchData(criteria);
						listGrid.drawGrid();
					}
				});

		// 订单操作ToolScript
		toolStripPanel = new DeliveryOrderToolStrip(listGrid,modName);

		// 订单明细列表
		itemListGrid = new DeliveryOrderItemListGrid();

		listGrid.addRecordClickHandler(new RecordClickHandler() {
			public void onRecordClick(RecordClickEvent event) {
				tool.onCreateDataSource(
						"supplierSupport.repairClaim.repairDeliveryOrder",
						"PickupDeliveryVanningItems_datasource",
						new PostDataSourceInit() {
							public void doPostOper(DataSource dataSource,
									DataInfo dataInfo) {
								itemListGrid.setDataSource(dataSource);
								Criteria criteria = new Criteria();
								criteria.addCriteria("_key", "fetchItem");
								criteria.addCriteria("_r",
										String.valueOf(Math.random()));
								criteria.addCriteria("orderId", listGrid
										.getSelectedRecord().getAttribute("id"));
								itemListGrid.fetchData(criteria);
								itemListGrid.drawGrid();
							}
						});
			}
		});

		VLayout v = new VLayout();
		v.setLayoutTopMargin(5);
		v.setMembersMargin(2);
		v.setWidth100();
		v.addMember(toolStripPanel);

		// 主容器
		SectionStack sStack = new SectionStack();
		sStack.setVisibilityMode(VisibilityMode.MULTIPLE);
		sStack.setAnimateSections(true);

		// 订单容器
		SectionStackSection siStackSection = new SectionStackSection("发货指令");
		siStackSection.addItem(listGrid);
		siStackSection.setExpanded(true);
		sStack.addSection(siStackSection);

		// 订单明细容器
		SectionStackSection siItemStackSection = new SectionStackSection(
				"发货指令项");
		siItemStackSection.setItems(itemListGrid);

		siItemStackSection.setExpanded(true);
		sStack.addSection(siItemStackSection);

		v.addMember(sStack);
		return v;
	}

	protected boolean isTopIntro() {
		return false;
	}

}