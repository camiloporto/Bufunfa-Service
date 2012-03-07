package br.com.bufunfa.finance.ui.account;

import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.TreeNode;
import org.springframework.context.annotation.Scope;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.stereotype.Controller;

@RooSerializable
@Controller
@Scope("session")
//@Component(value="contaView")
public class AccountController {
	
	private AccountTreeUI accountTree = new AccountTreeUI();

	public AccountController() {
	}

	public AccountTreeUI getAccountTree() {
		return accountTree;
	}

	public void addItem() {
		accountTree.addItem();
	}

	public void deleteItem() {
		accountTree.deleteItem();
	}

	public boolean isEditing() {
		return accountTree.isEditing();
	}

	public void saveItem() {
		accountTree.saveItem();
	}

	public void showEditItemForm() {
		accountTree.showEditItemForm();
	}

	public void showNewItemForm() {
		accountTree.showNewItemForm();
	}

	public void updateItem() {
		accountTree.updateItem();
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
		return findItemsByNameLike2(name, getAccountTree().getRootNode(),
				itemsFound);
	}

	private List<AccountTreeItemUI> findItemsByNameLike2(String name,
			TreeNode root, List<AccountTreeItemUI> result) {
		if (root.isLeaf()) {
			AccountTreeItemUI item = (AccountTreeItemUI) root.getData();
			if (item == null)
				return result;
			if (item.getAccountName().toLowerCase().contains(name.toLowerCase())) {
				result.add(item);
			}
		} else {
			List<TreeNode> children = root.getChildren();
			for (TreeNode nextChild : children) {
				result = findItemsByNameLike2(name, nextChild, result);
			}
		}
		return result;
	}

}
