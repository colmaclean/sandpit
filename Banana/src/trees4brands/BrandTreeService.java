package trees4brands;

import java.util.Map;
import java.util.TreeMap;

public class BrandTreeService {

    public Map<Integer, TreeNode> setup() {
        Map<Integer, TreeNode> treeMap = new TreeMap<Integer, TreeNode>();
        
        treeMap.put(10, new TreeNode(new Integer[] {20, 30, 40}, null));
        treeMap.put(20, new TreeNode(new Integer[] {50, 60}, null));
        treeMap.put(30, new TreeNode(null, new Integer[] {1, 2, 3}));
        treeMap.put(40, new TreeNode(null, new Integer[] {4}));
        treeMap.put(50, new TreeNode(new Integer[] {70}, new Integer[] {6}));
        treeMap.put(60, new TreeNode(null, new Integer[] {7}));
        treeMap.put(70, new TreeNode(null, new Integer[] {5}));
        
        return treeMap;
    }
    
    public void testTree(Map<Integer, TreeNode> treeMap) {
        for (Integer categoryId : treeMap.keySet()) {
            TreeNode node = treeMap.get(categoryId);
            System.out.print("\nCategory " + categoryId + ": ");
            traverseKids(treeMap, node);
        }
    }
    
    private void traverseKids(Map<Integer, TreeNode> treeMap, TreeNode node) {
        printArticleIds(node);
        if (node != null && node.getChildIds() != null) {
            for (Integer childId : node.getChildIds()) {
                TreeNode childNode = treeMap.get(childId);
                if (childNode != null) {
                    traverseKids(treeMap, childNode);
                }
            }
        }
    }
    
    private void printArticleIds(TreeNode treeNode) {
        if (treeNode != null && treeNode.getArticleIds() != null) {
            for (Integer articleId : treeNode.getArticleIds()) {
                System.out.print(articleId + " ");
            }
        }
    }
}
