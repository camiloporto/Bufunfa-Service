package br.com.bufunfa.finance.account.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;

import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.stereotype.Controller;

import br.com.bufunfa.finance.account.i18n.AccountMessageSource;
import br.com.bufunfa.finance.account.modelo.Account;
import br.com.bufunfa.finance.account.modelo.AccountSystem;
import br.com.bufunfa.finance.account.service.AccountSystemService;
import br.com.bufunfa.finance.core.controller.FacesMessageUtil;
import br.com.bufunfa.finance.user.modelo.User;

@RooSerializable
@Controller
@Scope("session")
public class AccountController {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5521269651219349380L;

	private AccountTreeUI accountTree;
	
	@Autowired
	private AccountSystemService accountSystemService;
	
	@Autowired
	private AccountMessageSource messageSource;
	
	private AccountSystem loggedAccountSystem;

	public AccountController() {
	}
	
	
	public void loadAccountHierarchy(User u) {
		loggedAccountSystem = accountSystemService.findAccountSystemByUserId(u.getEmail());
		Account rootAccount = accountSystemService.findAccount(loggedAccountSystem.getRootAccountId());
		accountTree = new AccountTreeUI(loggedAccountSystem, rootAccount);
		accountTree.setAccountSystemService(accountSystemService);
		accountTree.setMessageSource(messageSource);
		accountTree.init();
	}
	
	public AccountSystem getLoggedAccountSystem() {
		return loggedAccountSystem;
	}

	public AccountTreeUI getAccountTree() {
		return accountTree;
	}

	public void addItem() {
		accountTree.addItem();
		String message = messageSource.getMessage(
				AccountMessageSource.ACCOUNT_ADDED_SUCCESSFULLY, 
				null, new Locale("pt", "BR"));
		FacesMessageUtil.addFacesMessage(message, FacesMessage.SEVERITY_INFO);
	}

	public void deleteItem() {
		accountTree.deleteItem();
		String message = messageSource.getMessage(
				AccountMessageSource.ACCOUNT_REMOVED_SUCCESSFULLY, 
				null, new Locale("pt", "BR"));
		FacesMessageUtil.addFacesMessage(message, FacesMessage.SEVERITY_INFO);
	}

	public boolean isEditing() {
		return accountTree.isEditing();
	}

	public void saveItem() {
		if(isEditing())
			updateItem();
		if(!isEditing())
			addItem();
		
	}

	public void showEditItemForm() {
		accountTree.showEditItemForm();
	}

	public void showNewItemForm() {
		accountTree.showNewItemForm();
	}

	public void updateItem() {
		accountTree.updateItem();
		String message = messageSource.getMessage(
				AccountMessageSource.ACCOUNT_UPDATED_SUCCESSFULLY,
				null, new Locale("pt", "BR"));
		FacesMessageUtil.addFacesMessage(message, FacesMessage.SEVERITY_INFO);
	}

	/**
	 * * Busca o item de conta pelo nome (ignorando case) * @param name nome da
	 * conta * @return o item. null se nao encontrar
	 */
	public AccountTreeItemUI findLeafItemByName(String name) {
		if (name == null)
			throw new IllegalArgumentException("param name is required");
		List<AccountTreeItemUI> singleResult = findLeafItemsByNameLike(name);
		for (AccountTreeItemUI candidate : singleResult) {
			if (candidate.getAccountName().toLowerCase()
					.equals(name.toLowerCase())) {
				return candidate;
			}
		}
		return null;
	}

	/**
	 * * Retorna contas folhas por um nome parcial * @param name parte do nome
	 * da conta * @return
	 */
	public List<AccountTreeItemUI> findLeafItemsByNameLike(String name) {
		List<AccountTreeItemUI> itemsFound = new ArrayList<AccountTreeItemUI>();
		findItemsByNameLike2(name, getAccountTree().getRootNode(),
				itemsFound);
		return itemsFound;
	}
	
	/**
	 * * Retorna contas folhas por um nome parcial * @param name parte do nome
	 * da conta * @return
	 */
	public List<SelectItem> getLeafItems() {
		List<AccountTreeItemUI> itemsFound = new ArrayList<AccountTreeItemUI>();
		findItemsByNameLike2("Con", getAccountTree().getRootNode(),
				itemsFound);
		List<SelectItem> result = new ArrayList<SelectItem>();
		for (AccountTreeItemUI accountTreeItemUI : itemsFound) {
			result.add(new SelectItem(accountTreeItemUI.getId(), accountTreeItemUI.getAccountName()));
		}
		return result;
	}

	private List<AccountTreeItemUI> findItemsByNameLike2(String name,
			TreeNode root, List<AccountTreeItemUI> result) {
		if (root.isLeaf()) {
			AccountTreeItemUI item = (AccountTreeItemUI) root.getData();
			if (item == null)
				return result;
			result.add(item);
			
//			if (item.getAccountName().toLowerCase().contains(name.toLowerCase())) {
//				result.add(item);
//			}
		} else {
			List<TreeNode> children = root.getChildren();
			for (TreeNode nextChild : children) {
				result = findItemsByNameLike2(name, nextChild, result);
			}
		}
		return result;
	}


	public AccountTreeItemUI findAccountItemById(Long id) {
		AccountTreeItemUI item = findAccountItemById(id, getAccountTree().getRootNode());
		return item;
	}
	
	private AccountTreeItemUI findAccountItemById(Long id, TreeNode root) {
		AccountTreeItemUI item = null;
		if(root.isLeaf()) {
			item = (AccountTreeItemUI) root.getData();
			if (item == null)
				return null;
			if (item.getId().equals(id)) {
				return item;
			}
		} else {
			List<TreeNode> children = root.getChildren();
			for (TreeNode nextChild : children) {
				item = findAccountItemById(id, nextChild);
				if(item != null) {
					return item;
				}
			}
		}
		return item;
	}

}
