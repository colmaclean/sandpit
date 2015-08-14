package trees4brands;

import java.util.HashSet;
import java.util.Set;

public class TreeNode {
    Set<Integer> childIds;
    Set<Integer> articleIds;
    
    public TreeNode(Integer[] childIds, Integer[] articleIds) {
        if (childIds != null) {
            this.childIds = new HashSet<Integer>();
            for (Integer childId : childIds) {
                this.childIds.add(childId);
            }
        }
        if (articleIds != null) {
            this.articleIds = new HashSet<Integer>();
            for (Integer articleId : articleIds) {
                this.articleIds.add(articleId);
            }            
        }
    }

    public Set<Integer> getChildIds() {
        return childIds;
    }

    public void setChildIds(Set<Integer> childIds) {
        this.childIds = childIds;
    }

    public Set<Integer> getArticleIds() {
        return articleIds;
    }

    public void setArticleIds(Set<Integer> articleIds) {
        this.articleIds = articleIds;
    }
}
