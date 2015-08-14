package trees4brands;

import java.util.Map;

public class TreeRunner {

    public static void main (String... args) {
        BrandTreeService service = new BrandTreeService();
        Map<Integer, TreeNode> treeMap = service.setup();
        service.testTree(treeMap);
    }
}
