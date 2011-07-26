package com.skynet.spms.client.gui.customerService.leaseService.leasecontract.view;

import com.google.gwt.user.client.ui.Label;
import com.skynet.spms.client.entity.DataInfo;
import com.skynet.spms.client.feature.data.DataSourceTool;
import com.skynet.spms.client.feature.data.DataSourceTool.PostDataSourceInit;
import com.skynet.spms.client.gui.base.DicKey;
import com.skynet.spms.client.gui.base.SuperWindow;
import com.skynet.spms.client.gui.base.Utils;
import com.skynet.spms.client.gui.contractManagement.tag.TagEnum;
import com.skynet.spms.client.gui.customerService.common.DSKey;
import com.skynet.spms.client.gui.customerService.commonui.AttachmentCanvas;
import com.skynet.spms.client.gui.customerService.commonui.BaseAddressForm;
import com.skynet.spms.client.gui.customerService.commonui.ContractProvisionPanel;
import com.skynet.spms.client.gui.customerService.leaseService.model.MainModelLocator;
import com.skynet.spms.client.widgets.form.LayoutDynamicForm;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.BlurEvent;
import com.smartgwt.client.widgets.form.fields.events.BlurHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

/**
 * 查看窗体
 * 
 * @author tqc
 * 
 */
public class LeaseContractViewWindow extends SuperWindow {
	private MainModelLocator modelLocator = MainModelLocator.getInstance();

	private LayoutDynamicForm itemLdf;// 订单明细Form

	SelectItem item1;
	SelectItem item2;
	TextItem item3;
	SelectItem item4;
	SelectItem item5;
	SelectItem item8;
	TextItem item9;
	SelectItem item10;
	SelectItem item11;
	SelectItem item12;
	DateItem item13;
	TextAreaItem item14;
	DateItem item15;
	TextItem item16;
	TextItem item17;
	SelectItem item18;
	TextItem item19;
	TextItem item20;
	TextItem item21;
	TextItem item22;
	ListGrid lg;

	public LeaseContractViewWindow() {
		this.setTitle("查看客户租赁合同");
		/** 主面板 * */
		VLayout vmain = new VLayout();
		vmain.setWidth100();
		vmain.setHeight100();

		/** 面板1 * */
		TabSet tabSet = new TabSet();
		lg = getRightGrid();
		ContractBaseViewForm baseForm = new ContractBaseViewForm(lg);
		Tab baseTab = new Tab("合同基本信息");
		baseTab.setPane(baseForm);

		Tab addressTab = new Tab("收发地址");
		addressTab.setPane(new BaseAddressForm(BaseAddressForm.Type.view));

		Tab attachmentTab = new Tab("合同附件");
		attachmentTab
				.setPane(new AttachmentCanvas(modelLocator.LeaseContractId));

		Tab provisionTab = new Tab("合同条款");
		provisionTab.setPane(new ContractProvisionPanel(baseForm.form,
				TagEnum.RepairInspectClaimContractTemplate));

		tabSet.setTabs(baseTab, addressTab, attachmentTab, provisionTab);
		tabSet.setHeight(380);
		vmain.addMember(tabSet);
		/** 面板2 * */
		HLayout twoView = getShowGridView();
		twoView.setLayoutTopMargin(10);
		vmain.addMember(twoView);

		/** 面板3 * */
		VLayout threeView = getProcurementItemView();
		vmain.addMember(threeView);

		addMemberToLeft(vmain);
	}

	// 布局2
	private HLayout getShowGridView() {
		HLayout h = new HLayout();
		h.setWidth100();
		h.setHeight100();

		VLayout rightLayout = new VLayout();
		Label rightLb = new Label("租赁合同明细项");
		rightLb.setHeight("20");
		rightLayout.addMember(rightLb);
		rightLayout.addMember(lg);

		h.addMember(rightLayout);
		return h;
	}

	// 租赁合同明细项ListGrid
	private ListGrid getRightGrid() {
		lg = new ListGrid();
		lg.setCanEdit(false);
		Utils.setListGridHeight(lg);
		lg.setShowRowNumbers(true);
		lg.setCanRemoveRecords(false);
		final DataSourceTool dataSourceTool = new DataSourceTool();
		dataSourceTool.onCreateDataSource(DSKey.C_LEASECONTRACT_ITEM,
				DSKey.C_LEASECONTRACT_TIEM_DS, new PostDataSourceInit() {
					public void doPostOper(DataSource dataSource,
							DataInfo dataInfo) {
						lg.setDataSource(dataSource);
						StringBuilder builder = new StringBuilder();
						builder.append(modelLocator.LeaseContractId);
						Criteria criteria = new Criteria();
						criteria.addCriteria("id", builder.toString());
						criteria.addCriteria("_r", String
								.valueOf(Math.random()));
						lg.fetchData(criteria);

						ListGridField field2 = new ListGridField("partNumber",
								"件号");

						ListGridField field4 = new ListGridField("quantity",
								"租赁数量");
						Utils.formatQuantityField(field4);

						ListGridField field6 = new ListGridField("price",
								"租金总额");

						lg.setFields(field2, field4, field6);

					}
				});

		// 绑定假数据
		lg.addRecordClickHandler(new RecordClickHandler() {
			public void onRecordClick(RecordClickEvent event) {
				itemLdf.reset();
				itemLdf.editSelectedData(lg);
			}
		});
		return lg;
	}

	// 布局3(明细添加)
	/**
	 * @return
	 */

	private VLayout getProcurementItemView() {

		VLayout v = new VLayout();
		v.setWidth100();
		v.setHeight100();
		v.setGroupTitle("租赁合同明细项");
		v.setIsGroup(true);
		v.setLayoutLeftMargin(10);

		itemLdf = new LayoutDynamicForm();
		itemLdf.setNumCols(6);
		itemLdf.setWidth100();

		final TextItem leaseContractId = new TextItem();
		leaseContractId.setVisible(false);
		leaseContractId.setName("leaseContractTemplate.id");

		item1 = Utils.getPartNumberList();
		item1.setName("partNumber");
		item1.setTitle("件号");

		item2 = new SelectItem();
		item2.setTitle("制造商");
		item2.setName("m_ManufacturerCode.id");
		item2.setValueMap(DicKey.MANUFACTURERCODE);

		item3 = new TextItem();
		item3.setName("keyword");
		item3.setTitle("关键字");

		item4 = new SelectItem();
		item4.setTitle("证书类型");
		item4.setName("m_CertificateType");

		item5 = new SelectItem();
		item5.setTitle("备件状态代码");
		item5.setName("m_ConditionCode");

		item8 = new SelectItem();
		item8.setTitle("适用机型");
		item8.setName("m_ModelofApplicabilityCode");

		item9 = new TextItem();
		item9.setTitle("租赁数量");
		item9.setName("quantity");
		item9.addChangedHandler(new ChangedHandler() {
			public void onChanged(ChangedEvent event) {
				item21.setValue(Float.parseFloat(item17.getValue().toString())
						* Integer.parseInt(item16.getValue().toString())
						* Integer.parseInt(item9.getValue().toString())
						+ Float.parseFloat(item19.getValue().toString()));
			}
		});

		item10 = new SelectItem();
		item10.setTitle("包装代码");
		item10.setName("m_PackagingCode");

		item11 = new SelectItem();
		item11.setTitle("单位");
		item11.setName("m_UnitOfMeasureCode");

		item12 = new SelectItem();
		item12.setTitle("币种");
		item12.setName("m_InternationalCurrencyCode");

		item13 = new DateItem();
		item13.setTitle("租赁开始日期");
		item13.setName("startDate");
		item13.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);
		item13.setUseTextField(true);
		item13.addBlurHandler(new BlurHandler() {
			public void onBlur(BlurEvent event) {
				long starttime = item15.getValueAsDate().getTime();

				long endtime = item13.getValueAsDate().getTime();

				int modDay = 24 * 60 * 60 * 1000;

				int day = (int) ((starttime - endtime) / modDay);
				item16.setValue(day);
			}
		});

		item14 = new TextAreaItem();
		item14.setTitle("申请单备注");
		item14.setName("remarkText");
		item14.setHeight(50);

		item15 = new DateItem();
		item15.setTitle("租赁结束日期");
		item15.setName("endDate");
		item15.setUseTextField(true);
		item15.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);
		item15.addBlurHandler(new BlurHandler() {
			public void onBlur(BlurEvent event) {

				long starttime = item15.getValueAsDate().getTime();

				long endtime = item13.getValueAsDate().getTime();

				int modDay = 24 * 60 * 60 * 1000;

				int day = (int) ((starttime - endtime) / modDay);
				item16.setValue(day);
			}
		});

		item16 = new TextItem();
		item16.setTitle("租赁天数");
		item16.setName("leaseDays");
		item16.addChangedHandler(new ChangedHandler() {
			public void onChanged(ChangedEvent event) {
				item21.setValue(Float.parseFloat(item17.getValue().toString())
						* Integer.parseInt(item16.getValue().toString())
						* Integer.parseInt(item9.getValue().toString())
						+ Float.parseFloat(item19.getValue().toString()));
			}
		});
		item17 = new TextItem();
		item17.setTitle("标准单位租金");
		item17.setName("generalRentOfUnit");
		item17.addChangedHandler(new ChangedHandler() {
			public void onChanged(ChangedEvent event) {
				item21.setValue(Float.parseFloat(item17.getValue().toString())
						* Integer.parseInt(item16.getValue().toString())
						* Integer.parseInt(item9.getValue().toString())
						+ Float.parseFloat(item19.getValue().toString()));
			}
		});

		item18 = new SelectItem();
		item18.setTitle("租金计算单位");
		item18.setName("m_UnitOfRent");

		item19 = new TextItem();
		item19.setTitle("手续费");
		item19.setName("factorage");
		item19.addChangedHandler(new ChangedHandler() {
			public void onChanged(ChangedEvent event) {
				item21.setValue(Float.parseFloat(item17.getValue().toString())
						* Integer.parseInt(item16.getValue().toString())
						* Integer.parseInt(item9.getValue().toString())
						+ Float.parseFloat(item19.getValue().toString()));
			}
		});
		item20 = new TextItem();
		item20.setTitle("延期单位租金");
		item20.setName("extendedRentOfUnit");

		item21 = new TextItem();
		item21.setTitle("总租金");
		item21.setName("price");

		item22 = new TextItem();
		item22.setTitle("原价值");
		item22.setName("originalValue");
		itemLdf.setFields(leaseContractId, item1, item2, item3, item4, item5,
				item8, item9, item10, item11, item12, item13, item15, item16,
				item17, item18, item19, item21, item22, item14);
		Utils.setReadOnlyForm(itemLdf);
		v.addMember(itemLdf);

		return v;
	}
}
