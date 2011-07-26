package com.skynet.spms.client.gui.commonOrder.pickup.ui.view;

import com.google.gwt.user.client.ui.Label;
import com.skynet.spms.client.entity.DataInfo;
import com.skynet.spms.client.feature.data.DataSourceTool;
import com.skynet.spms.client.feature.data.DataSourceTool.PostDataSourceInit;
import com.skynet.spms.client.gui.base.BaseWindow;
import com.skynet.spms.client.gui.commonOrder.pickup.model.DataModelLocator;
import com.smartgwt.client.core.Rectangle;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * 
 * @author tqc
 * 
 */
public class PickUpOrderViewWindow extends BaseWindow {


	/** 装箱 Grid */
	ListGrid vanningListGrid = null;
	/** 装箱物品 Grid */
	ListGrid vanningItemListGrid = null;

	/**
	 * 无需传递数据源 重载
	 * 
	 * @param opm
	 *            当前操作方式
	 */
	public PickUpOrderViewWindow(String windowTitle, boolean isMax,
			Rectangle srcRect, ListGrid listGrid, String iconUrl,
			String contractNumber, String contractType) {
		super(windowTitle, isMax, srcRect, listGrid, iconUrl);
	}

	@Override
	protected Canvas getLeftLayout(Rectangle srcRect, ListGrid listGrid) {
		vanningListGrid = new ListGrid();
		vanningItemListGrid = new ListGrid();
		/**
		 * 构建箱信息grid数据源
		 */
		final DataSourceTool tool = new DataSourceTool();
		tool.onCreateDataSource(
				"supplierSupport.repairClaim.repairDeliveryOrder",
				"PickupDeliveryVanning_datasource", new PostDataSourceInit() {
					@Override
					public void doPostOper(DataSource dataSource,
							DataInfo dataInfo) {
						vanningListGrid.setDataSource(dataSource);
						Criteria criteria = new Criteria();
						criteria.addCriteria("orderID", DataModelLocator
								.getInstance().modifyOrderGrid
								.getSelectedRecord().getAttribute("id"));
						criteria.addCriteria("_r",
								String.valueOf(Math.random()));
						vanningListGrid.fetchData(criteria, new DSCallback() {
							public void execute(DSResponse response,
									Object rawData, DSRequest request) {
								buildVanningListGrid();
							}
						});

					}
				});
		/**
		 * 构建发货单明细grid数据源
		 */
		tool.onCreateDataSource(
				"supplierSupport.repairClaim.repairDeliveryOrder",
				"PickupDeliveryVanningItems_datasource",
				new PostDataSourceInit() {
					@Override
					public void doPostOper(DataSource dataSource,
							DataInfo dataInfo) {
						vanningItemListGrid.setDataSource(dataSource);
						vanningItemListGrid.setAutoSaveEdits(false);
						Criteria criteria = new Criteria();
						criteria.addCriteria("orderId", DataModelLocator
								.getInstance().modifyOrderGrid
								.getSelectedRecord().getAttribute("id"));
						criteria.addCriteria("_r",
								String.valueOf(Math.random()));
						vanningItemListGrid.fetchData(criteria,
								new DSCallback() {
									@Override
									public void execute(DSResponse response,
											Object rawData, DSRequest request) {
										buildVanningItemListGrid();
									}
								});

					}
				});

		this.setTitle("提货指令查看");
		VLayout vmain = new VLayout();
		vmain.setWidth100();
		vmain.setHeight100();

		PickUpBaseViewForm baseForm = new PickUpBaseViewForm();
		baseForm.setHeight(313);
		vmain.addMember(baseForm);

		VLayout twoView = getShowGridView();
		twoView.setLayoutTopMargin(10);
		vmain.addMember(twoView);

		return vmain;
	}

	private VLayout getShowGridView() {
		VLayout v = new VLayout();
		v.setMembersMargin(5);
		v.setLayoutTopMargin(3);
		HLayout h = new HLayout();
		h.setWidth100();
		h.setHeight100();
		h.setMembersMargin(10);
		VLayout rightLayout = new VLayout();
		HLayout titleRightTopHL = new HLayout();
		titleRightTopHL.setHeight(30);
		titleRightTopHL.setWidth100();

		Label titleRightTopLb = new Label("装箱信息");
		titleRightTopLb.setWidth("100");
		titleRightTopLb.setHeight("15");
		
		titleRightTopHL.addMember(titleRightTopLb);
		rightLayout.addMember(titleRightTopHL);
		rightLayout.addMember(vanningListGrid);

		Label rightBottomLb = new Label("提货指令明细信息");
		rightBottomLb.setHeight("15");
		rightLayout.addMember(rightBottomLb);
		rightLayout.addMember(vanningItemListGrid);
		h.addMember(rightLayout);

		HLayout btnGroup = new HLayout();
		btnGroup.setMembersMargin(3);
		btnGroup.setLayoutLeftMargin(50);
		btnGroup.setMargin(5);

		v.addMember(h);
		v.addMember(btnGroup);
		return v;
	}


	/**
	 * 提货指令装箱信息
	 * 
	 * @return
	 */
	private void buildVanningListGrid() {
		vanningListGrid.setHeight("120");
		vanningListGrid.setShowRowNumbers(true);
		vanningListGrid.setCanEdit(false);
		ListGridField field0 = new ListGridField("orderID", "指令主键");
		field0.setHidden(true);
		ListGridField field1 = new ListGridField("itemNo", "项号");
		field1.setHidden(true);
		ListGridField field2 = new ListGridField("packingListNumber", "装箱单号");
		field2.setRequired(true);
		ListGridField field3 = new ListGridField("pacakgeNumber", "箱号");
		field3.setRequired(true);
		ListGridField field4 = new ListGridField("containerSizeandWeight", "尺寸");
		field4.setRequired(true);
		ListGridField field5 = new ListGridField("billOfLadingWeight", "净重");
		field5.setRequired(true);
		field5.setAlign(Alignment.RIGHT);
		vanningListGrid.setFields(field0, field1, field2, field3, field4,
				field5);

	}

	/**
	 * 提货指令明细项
	 * 
	 * @return
	 */
	private void buildVanningItemListGrid() {
		vanningItemListGrid.setEditEvent(ListGridEditEvent.CLICK);
		vanningItemListGrid.setAutoFetchData(true);
		vanningItemListGrid.setHeight("120");
		vanningItemListGrid.setShowRowNumbers(true);
		vanningItemListGrid.setCanEdit(false);
		ListGridField field0 = new ListGridField("orderID", "指令主键");
		field0.setHidden(true);
		ListGridField field1 = new ListGridField("itemNo", "项号");
		field1.setHidden(true);
		ListGridField field2 = new ListGridField("contractNumber", "合同号");
		field2.setHidden(true);
		ListGridField field3 = new ListGridField("partNumber", "件号");
		field3.setCanEdit(false);
		ListGridField field4 = new ListGridField("vanningID", "箱号");
		field4.setRequired(true);
		field4.setEditorType(new SelectItem());
		ListGridField field6 = new ListGridField("quantity", "提货数量");
		field6.setCanEdit(false);
		ListGridField field7 = new ListGridField("unitPriceAmount", "单价");
		field7.setCanEdit(false);
		ListGridField field8 = new ListGridField("totalAmount", "总价");
		field8.setCanEdit(false);

		vanningItemListGrid.setFields(field0, field1, field2, field3, field4,
				field6, field7, field8);

	}




}
