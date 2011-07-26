package com.skynet.spms.client.gui.supplierSupport.procurementOrder.transferOrder.add;

import java.util.LinkedHashMap;
import com.skynet.spms.client.gui.base.BaseWindow;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.skynet.spms.client.entity.DataInfo;
import com.skynet.spms.client.feature.data.DataSourceTool;
import com.skynet.spms.client.feature.data.DataSourceTool.PostDataSourceInit;
import com.skynet.spms.client.gui.base.BtnsHLayout;
import com.skynet.spms.client.gui.base.CodeRPCTool;
import com.skynet.spms.client.gui.base.DicKey;
import com.skynet.spms.client.gui.base.EnumTool;
import com.skynet.spms.client.gui.base.Utils;
import com.skynet.spms.client.gui.customerService.commonui.CommonWindow;
import com.skynet.spms.client.gui.supplierSupport.common.DSKey;
import com.skynet.spms.client.gui.supplierSupport.procurementOrder.procurementOrder.model.ProcurementOrderModelLocator;
import com.skynet.spms.client.service.BaseCodeService;
import com.skynet.spms.client.service.BaseCodeServiceAsync;
import com.skynet.spms.client.tools.UserTools;
import com.skynet.spms.client.vo.PartInfoVO;
import com.skynet.spms.client.widgets.form.LayoutDynamicForm;
import com.skynet.spms.client.widgets.form.fields.DicSelectItem;
import com.skynet.spms.client.widgets.grid.FilterListGrid;
import com.smartgwt.client.core.Rectangle;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

/*******************************************************************************
 * 主订单 与 明细 添加页面
 * 
 * @author Tony FANG
 * 
 */
public class TransferOrderAddWindow extends BaseWindow {

	private ProcurementOrderModelLocator modelLocator;

	private ListGrid[] listGrids = null;

	private LayoutDynamicForm ldf;// 主订单Form

	private LayoutDynamicForm itemLdf;// 订单明细Form

	private BtnsHLayout btnsLayout;// 主订单操作按钮容器

	private BtnsHLayout itemBtnsLayout;// 明细操作按钮

	private IButton saveItemBtn;

	private String primaryKey;// 主表id

	private FilterListGrid procurementOrderLG;// 采购订单选择Grid

	private FilterListGrid itemListGrid;// 新采购询价单Grid

	private FormItem item1;
	private SelectItem item2;
	private FormItem item3;
	private SelectItem item4;
	private FormItem item5;
	private SelectItem item6;
	private DateItem item7;
	private FormItem item8;
	private FormItem item9;
	private SelectItem item10;
	private FormItem item11;
	private FormItem item12;
	private FormItem item13;
	private FormItem item14;
	private FormItem item15;
	private FormItem item16;

	private static BaseCodeServiceAsync service = GWT
			.create(BaseCodeService.class);

	/**
	 * 无需传递数据源 重载
	 * 
	 * @param opm
	 *            当前操作方式
	 */
	public TransferOrderAddWindow(String windowTitle, boolean isMax,
			Rectangle srcRect, ListGrid listGrid, String iconUrl) {
		super(windowTitle, isMax, srcRect, listGrid, iconUrl);
	}

	@Override
	protected Canvas getLeftLayout(Rectangle srcRect, ListGrid listGrid) {
		this.setTitle("新建调拨单");
		modelLocator = ProcurementOrderModelLocator.getInstance();
		itemListGrid = new FilterListGrid();

		/** 主面板* */
		VLayout vmain = new VLayout();
		/**
		 * vmain.setLayoutMargin(3); 设置此属性由于总体宽度不够 就会报one or more 错 解决此问题的方式
		 * 只有调整元素 高度与宽度；或者 直接设置主面板宽度
		 */
		vmain.setWidth100();
		vmain.setHeight100();

		// /**面板1**/
		VLayout oneView = getPrimaryView();
		oneView.setHeight("35%");
		oneView.setWidth100();
		vmain.addMember(oneView);

		/** 面板2 * */
		HLayout twoView = getShowGridView();
		twoView.setHeight("30%");
		twoView.setWidth100();
		twoView.setLayoutTopMargin(10);
		vmain.addMember(twoView);

		// /**面板3**/
		VLayout threeView = getItemView();
		// threeView.setHeight(180);
		threeView.setLayoutTopMargin(10);
		threeView.setHeight("35%");
		threeView.setWidth100();
		vmain.addMember(threeView);

		return vmain;
	}

	public void disableBtn() {
		this.btnsLayout.disable();
	}

	// 布局一
	private VLayout getPrimaryView() {
		VLayout v = new VLayout();
		v.setWidth100();
		v.setHeight100();
		v.setGroupTitle("调拨单");
		v.setIsGroup(true);
		v.setLayoutLeftMargin(10);

		ldf = new LayoutDynamicForm();

		ldf.setNumCols(6);
		ldf.setWidth100();
		ldf.setHeight100();
		ldf.setCellPadding(2);

		ldf.setDataSource(modelLocator.procurementTransferDS);

		ListGridRecord selectLGR = modelLocator.procurementOrderListGrid
				.getSelectedRecord();

		// 调拨单编号
		TextItem item_1 = new TextItem("transferSheetNumber");
		item_1.setValue("业务编号系统自动生成");
		item_1.setDisabled(true);

		// 采购指令号
		TextItem item_2 = new TextItem("orderNumber");
		item_2.setAttribute("readOnly", true);
		FormItemIcon fii2 = new FormItemIcon();
		item_2.setIcons(fii2);
		item_2.setValue(selectLGR.getAttribute("orderNumber"));

		// 优先级
		SelectItem item_3 = new SelectItem();
		item_3.setName("m_Priority");
		item_3.setValue(selectLGR.getAttribute("m_Priority"));

		// 调拨来源
		SelectItem item_4 = new SelectItem();
		item_4.setName("transferFrom.id");
		CodeRPCTool.bindData(CodeRPCTool.CAGECODE, item_4);

		// 联系人
		TextItem item_5 = new TextItem("linkman");

		// 飞机尾号
		SelectItem item_6 = new SelectItem();
		item_6.setName("airIdentificationNumber");
		item_6.setValue(selectLGR.getAttribute("airIdentificationNumber"));
		Utils.setAirIdentificationNumberItemDS(item_6);

		// 联系方式
		TextItem item_7 = new TextItem("contactInformation");
		item_7.setColSpan(3);

		// 交货方式
		SelectItem item_8 = new SelectItem();
		item_8.setName("m_DeliveryType");

		// 联系人
		final TextItem item_12 = new TextItem("carrierLinkman");
		item_12.setDisabled(true);

		// 联系方式
		final TextItem item_14 = new TextItem("carrierContactInformation");
		item_14.setDisabled(true);
		item_14.setColSpan(3);

		// 运代商
		final SelectItem item_11 = new SelectItem("m_CarrierName.id");
		CodeRPCTool.bindData(CodeRPCTool.CARRIERNAME, item_11);
		item_11.setDisabled(true);
		item_11.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				String id = item_11.getValueAsString();
				CodeRPCTool.bindCarrierData(id, item_12, item_14);
			}
		});

		// 由调拨来源企业指定运代
		CheckboxItem item_9 = new CheckboxItem();
		item_9.setName("CAGEAssignCarrier");
		item_9.setValue(true);
		item_9.setColSpan(3);
		item_9.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				if ((Boolean) event.getValue()) {
					item_14.setDisabled(true);
					item_11.setDisabled(true);
					item_12.setDisabled(true);
				} else {
					item_14.setDisabled(false);
					item_11.setDisabled(false);
					item_12.setDisabled(false);
				}
			}
		});

		// 运输方式
		final SelectItem item_10 = new SelectItem();
		item_10.setName("m_TransportationCode");

		// 贸易方式
		SelectItem item_13 = new SelectItem();
		item_13.setName("m_TradeMethods");

		// GTA协议
		final SelectItem item_15 = new SelectItem();
		item_15.setName("m_EnterpriseGTA");
		item_15.setMultiple(true);
		/**
		 * 初始化 初始化GTA协议下拉列表
		 */
		// item_15.setMultiple(true);
		// DataSourceTool dsTool = new DataSourceTool();
		// dsTool.onCreateDataSource(
		// DSKey.S_PROCUREMENTTRANSFERORDER,
		// DSKey.S_CUSTOMERREPAIRINSORDER_GTA_DS,
		// new PostDataSourceInit() {
		// public void doPostOper(
		// DataSource dataSource,
		// DataInfo dataInfo) {
		// item_15.setOptionDataSource(dataSource);
		// }
		// });
		//						

		// GTA协议
		// final SelectItem enterpriseGTAItem = new SelectItem();
		// DataSourceTool dsTool = new DataSourceTool();
		// dsTool.onCreateDataSource(DSKey.S_PROCUREMENTTRANSFERORDER,
		// DSKey.S_CUSTOMERREPAIRINSORDER_GTA_DS,
		// new PostDataSourceInit() {
		// public void doPostOper(DataSource dataSource,
		// DataInfo dataInfo) {
		// enterpriseGTAItem.setOptionDataSource(dataSource);
		// }
		// });
		// enterpriseGTAItem.setName("enterpriseIds");
		// enterpriseGTAItem.setMultiple(true);
		// enterpriseGTAItem.setColSpan(3);
		// enterpriseGTAItem.setWidth("100%");

		// 备注
		TextAreaItem item_16 = new TextAreaItem("remarkText");
		item_16.setColSpan(3);
		item_16.setRowSpan(3);
		item_16.setHeight("100%");
		item_16.setTitleVAlign(VerticalAlignment.TOP);

		// 制定人
		DicSelectItem item_17 = new DicSelectItem();
		item_17.setName("makeBy");
		item_17.setDisabled(true);
		item_17.setValue(UserTools.getCurrentUserName());

		// 项数总计
		DicSelectItem item_18 = new DicSelectItem();
		item_18.setName("itemCount");
		item_18.setDisabled(true);

		// 单位
		SelectItem item_19 = new SelectItem();
		item_19.setName("m_UnitOfMeasureCode");

		ldf.setFields(item_1, item_2, item_3, item_4, item_5, item_6, item_7,
				item_8, item_9, item_10, item_11, item_12, item_13, item_14,
				item_15, item_16, item_17, item_18, item_19);

		v.setMembers(ldf);

		btnsLayout = new BtnsHLayout();

		// 构建操作按钮
		final IButton saveBtn = new IButton("保存");
		final IButton cancelBtn = new IButton("关闭");
		// 仅保存
		saveBtn.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				ldf.saveData(new DSCallback() {
					public void execute(DSResponse response, Object rawData,
							DSRequest request) {
						SC.say("保存成功!");
						saveItemBtn.setDisabled(false);
						primaryKey = response.getData()[0].getAttribute("id");
					}
				});
			}

		});

		// 关闭
		cancelBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				destroy();
			}

		});
		btnsLayout.addMember(saveBtn);
		btnsLayout.addMember(cancelBtn);

		v.addMember(btnsLayout);

		// 刷新总金额
		Timer timer = Utils.startAmountTimer(itemListGrid, item_18, null,
				"amount");
		timer.scheduleRepeating(500);

		return v;
	}

	// 布局2
	private HLayout getShowGridView() {
		HLayout h = new HLayout();
		h.setWidth100();
		h.setHeight100();
		h.setMembersMargin(5);
		h.setLayoutTopMargin(3);

		VLayout leftLayout = new VLayout();
		Label leftLb = new Label("采购指令明细项");
		leftLb.setHeight("20");
		leftLayout.addMember(leftLb);// 先放label
		leftLayout.addMember(getLeftGrid());// 再放Grid

		VLayout rightLayout = new VLayout();
		Label rightLb = new Label("调拨明细项");
		rightLb.setHeight("20");
		rightLayout.addMember(rightLb);
		rightLayout.addMember(getRightGrid());

		h.addMember(leftLayout);
		h.addMember(rightLayout);
		return h;
	}

	// 布局3(明细添加)
	private VLayout getItemView() {
		VLayout v = new VLayout();
		v.setWidth100();
		v.setHeight100();
		v.setGroupTitle("采购指令明细");
		v.setIsGroup(true);
		v.setLayoutLeftMargin(10);

		itemLdf = new LayoutDynamicForm();
		itemLdf.setWidth100();
		itemLdf.setHeight100();
		itemLdf.setNumCols(4);
		itemLdf.setCellPadding(2);
		itemLdf.setMargin(5);

		final TextItem primaryKeyItem = new TextItem();
		primaryKeyItem.setVisible(false);
		primaryKeyItem.setName("procurementTransferSheet.id");

		itemLdf.setDataSource(modelLocator.procurementTransferItemDS);

		// 件号
		item1 = Utils.getPartNumberList();
		item1.setName("partNumber");
		item1.addChangedHandler(new ChangedHandler() {
			public void onChanged(ChangedEvent event) {
				PartInfoVO partVo = Utils.getPartInfoVO(item1);
				item2.setValue(partVo.getManufacturerCodeId());// 制造商
				item3.setValue(partVo.getAtaNumber());// ATA
				item5.setValue(partVo.getKeyword());// 关键字
				item10.setValue(partVo.getUnitOfMeasureCode());// 单位
			}
		});

		// 制造商
		item2 = new SelectItem();
		item2.setName("m_ManufacturerCode.id");
		item2.setDisplayField("code");
		CodeRPCTool.bindData(CodeRPCTool.MANUFACTURERCODE, item2);
		item2.setDisabled(true);

		// ATA章节号
		item3 = new TextItem("ATA");

		// 适用机型
		item4 = new SelectItem();
		item4.setName("m_ModelofApplicabilityCode");
		item4.setMultiple(true);

		// 关键字
		item5 = new TextItem("itemKeyword");

		// 随件资料
		item6 = new SelectItem();
		item6.setName("m_CertificateType");
		item6.setMultiple(true);
		EnumTool.setValueMap(EnumTool.CERTIFICATETYPE, item6);

		// 交货日期
		item7 = new DateItem("deliveryDate");
		item7.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);
		item7.setUseTextField(true);

		// 备注
		item8 = new TextAreaItem();
		item8.setName("remarkText");
		item8.setRowSpan(3);
		item8.setHeight("100%");
		item8.setTitleVAlign(VerticalAlignment.TOP);

		// 调拨数量
		item9 = new TextItem("plannedQuantity");

		// 单位
		item10 = new SelectItem("m_UnitOfMeasureCode");

		/** 隐藏属性 **/
		// 单价
		item11 = new TextItem("plannedUnitPrice");
		item11.setVisible(false);

		// 金额
		item12 = new TextItem("amount");
		item12.setVisible(false);

		itemLdf.setFields(primaryKeyItem, item1, item2, item3, item4, item5,
				item6, item7, item8, item9, item10);

		v.addMember(itemLdf);

		itemBtnsLayout = new BtnsHLayout();
		// 构建操作按钮
		// 构建操作按钮
		saveItemBtn = new IButton("保存明细");
		saveItemBtn.setDisabled(true);
		saveItemBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// if (procurementOrderLG.getSelectedRecord() != null
				// || itemListGrid.getSelectedRecord() != null
				// || item1.getValue() != null) {
				primaryKeyItem.setValue(primaryKey);
				if (itemLdf.validate()) {
					itemLdf.saveData(new DSCallback() {
						public void execute(DSResponse response,
								Object rawData, DSRequest request) {

							itemLdf.clearValues();
							itemLdf.editNewRecord();
							Utils.makeAllNotSelectLG(procurementOrderLG);
							Utils.makeAllNotSelectLG(itemListGrid);
							refreshItemListGrid();
						}
					});
				}
				// } else {
				// SC.say("请先选择采购指令明细项");
				// }
			}
		});
		// 清空按钮
		IButton cleanBtn = new IButton("清空");
		cleanBtn.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				itemLdf.editNewRecord();
			}

		});

		IButton closeBtn = new IButton("关闭");
		closeBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				destroy();
			}
		});
		itemBtnsLayout.setMembers(saveItemBtn, cleanBtn, closeBtn);
		v.addMember(itemBtnsLayout);

		return v;
	}

	// 采购指令选择
	private ListGrid getLeftGrid() {
		procurementOrderLG = new FilterListGrid();
		Utils.setListGridHeight(procurementOrderLG);
		procurementOrderLG.setDataSource(modelLocator.procurementOrderItemDs);
		String proOrderId = modelLocator.procurementOrderListGrid
				.getSelectedRecord().getAttribute("id");

		Criteria criteria = new Criteria();
		criteria.setAttribute("id", proOrderId);
		criteria.setAttribute("_r", String.valueOf(Math.random()));

		procurementOrderLG.fetchData(criteria);

		// 件号
		ListGridField field2 = new ListGridField("partNumber");

		// 关键字
		ListGridField field3 = new ListGridField("itemKeyword");
		field3.setHidden(true);

		// 需求日期
		ListGridField field4 = new ListGridField("deliveryDate");
		field4.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);

		// 计划采购数量
		ListGridField field5 = new ListGridField("plannedQuantity");
		Utils.formatQuantityField(field5);

		procurementOrderLG.setFields(field2, field3, field4, field5);

		Utils.makeAllCanFilter(procurementOrderLG);
		// 选中行事件
		procurementOrderLG.addCellClickHandler(new CellClickHandler() {

			public void onCellClick(CellClickEvent event) {
				// 先清空Form表单的值
				itemLdf.clearValues();
				// 新添加模式
				itemLdf.editNewRecord();
				// 再次给Form赋值
				setFormItemValues(event.getRecord());
				// 新报价单Grid都不选中
				Utils.makeAllNotSelectLG(itemListGrid);
			}
		});

		return procurementOrderLG;
	}

	// 新采购指令明细项
	private ListGrid getRightGrid() {

		itemListGrid.setCanRemoveRecords(true);
		Utils.setListGridHeight(itemListGrid);
		// 初始化订单数据源
		final DataSourceTool dataSourceTool = new DataSourceTool();
		// 初始化订单明细数据源
		dataSourceTool.onCreateDataSource(
				DSKey.S_PROCUREMENTTRANSFERORDER_ITEM,
				DSKey.S_PROCUREMENTTRANSFERORDER_ITEM_DS,
				new PostDataSourceInit() {
					public void doPostOper(DataSource dataSource,
							DataInfo dataInfo) {
						itemListGrid.setDataSource(dataSource);
						refreshItemListGrid();

						// 件号
						ListGridField field2 = new ListGridField("partNumber");

						// 关键字
						ListGridField field3 = new ListGridField("itemKeyword");
						field3.setHidden(true);

						// 需求日期
						ListGridField field4 = new ListGridField("deliveryDate");
						field4
								.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);

						// 调拨数量
						ListGridField field5 = new ListGridField(
								"plannedQuantity");
						Utils.formatQuantityField(field5);

						itemListGrid.setFields(field2, field3, field4, field5);

						Utils.makeAllCanFilter(itemListGrid);

						// 选中行事件
						itemListGrid.addCellClickHandler(new CellClickHandler() {

									public void onCellClick(CellClickEvent event) {
										// 先清空Form表单的值
										itemLdf.clearValues();
										// 将From定义为修改模式(连接服务器端时修改掉 ListGrid)
										itemLdf.editSelectedData(itemListGrid);
										// 报价单单Grid都不选中
										Utils.makeAllNotSelectLG(procurementOrderLG);
										//多选下拉菜单赋值
										Utils.setMultipleFormItemValue(item6);//随件资料

										service
												.getPartInfoByPartNumber(
														event
																.getRecord()
																.getAttribute(
																		"partNumber"),
														new AsyncCallback<PartInfoVO>() {

															@Override
															public void onFailure(
																	Throwable arg0) {
																// TODO
																// Auto-generated
																// method stub
																SC.say("失败");
															}

															@Override
															public void onSuccess(
																	PartInfoVO partInfoVO) {
																// TODO
																// Auto-generated
																// method stub

																item3
																		.setValue(partInfoVO
																				.getAtaNumber());
															}
														});
									}
								});
					}
				});

		return itemListGrid;
	}

	/**
	 * 单击Grid 给Form 赋值
	 * 
	 * @param lgRecord
	 */
	private void setFormItemValues(ListGridRecord lgRecord) {
		ListGridRecord lgr = lgRecord;
		// 单击询价单明细选择
		item1.setValue(lgr.getAttribute("partNumber"));
		item2.setValue(lgr.getAttribute("m_ManufacturerCode.id"));
		item3.setValue(lgr.getAttribute("ata"));
		item4.setValue(lgr.getAttribute("itemKeyword"));
		// 多选证书类型赋值
		Utils.setMultipleFormItemValue(item6, lgr
				.getAttributeAsDate("m_CertificateType"));
		item7.setValue(lgr.getAttributeAsDate("deliveryDate"));
		item8.setValue(lgr.getAttribute("remarkText"));
		item9.setValue(lgr.getAttribute("plannedQuantity"));
		item10.setValue(lgr.getAttribute("m_UnitOfMeasureCode"));

	}

	/**
	 * 刷新listGrid
	 */
	private void refreshItemListGrid() {
		if (primaryKey == null) {
			primaryKey = "-1";
		}
		Criteria criteria = new Criteria();
		criteria.setAttribute("id", primaryKey);
		criteria.setAttribute("_r", String.valueOf(Math.random()));
		itemListGrid.fetchData(criteria);
	}

}
