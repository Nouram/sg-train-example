package com.skynet.spms.client.gui.commonOrder;

import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.toolbar.ToolStripMenuButton;

public class MenuButtonBuilder {
	

	/** 发货 **/
	public static final String DELIVERY = "DELIVERY";
	/** 提货 **/
	public static final String PICKUP = "PICKUP";
	/** 全部 **/
	public static final String ALL = "ALL";

	public static ToolStripMenuButton create(ContractIndexKey key,
			ListGrid listGrid, String menuType,String businessType) {
		return build(key, listGrid, menuType,businessType);
	}

	private static ToolStripMenuButton build(ContractIndexKey key,
			ListGrid listGrid, String menuType,final String businessType) {
		final ToolStripMenuButton bussinessOrderBtn = new ToolStripMenuButton(
				"物流指令");
		
		//按钮构建
		Menu bussinessOrderMenu = new Menu();
		MenuItem bussinessOrderMenuItem = new MenuItem("新建发货指令");
		MenuItem bussinessOrderMenuItemEnd = new MenuItem("新建提货指令");
		if (MenuButtonBuilder.ALL.equals(menuType)) {
			bussinessOrderMenu.setItems(bussinessOrderMenuItem,
					bussinessOrderMenuItemEnd);
		} else if (MenuButtonBuilder.DELIVERY.equals(menuType)) {
			bussinessOrderMenu.setItems(bussinessOrderMenuItem);
		} else if (MenuButtonBuilder.PICKUP.equals(menuType)) {
			bussinessOrderMenu.setItems(bussinessOrderMenuItemEnd);
		}
		
		final DirectiveAddClickHandler handlerDelivery = new DirectiveAddClickHandler(
				DirectiveType.delivery, listGrid, key.name(), businessType);
		bussinessOrderMenuItem.addClickHandler(new ClickHandler() {
			public void onClick(MenuItemClickEvent event) {
				handlerDelivery.pop(bussinessOrderBtn,businessType);
			}
		});

		final DirectiveAddClickHandler handlerPickup = new DirectiveAddClickHandler(
				DirectiveType.pickup, listGrid, key.name(), businessType);
		bussinessOrderMenuItemEnd.addClickHandler(new ClickHandler() {
			public void onClick(MenuItemClickEvent event) {
				handlerPickup.pop(bussinessOrderBtn,businessType);
			}
		});
		bussinessOrderBtn.setMenu(bussinessOrderMenu);

		return bussinessOrderBtn;
	}
	

}
