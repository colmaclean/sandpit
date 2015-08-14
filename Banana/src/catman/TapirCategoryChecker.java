/**
 * App to identify categories which will currently not appear on the mobile apps via tapir interface.
 * 
 * @author colin.maclean
 */
package catman;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

public class TapirCategoryChecker {
    private Map<Integer, Category> catmap = new TreeMap<Integer, Category>();
    private Set<Category> doubleTapirs = new HashSet<Category>();
    private Set<Category> parentsOfDoubleTapirs = new HashSet<Category>();
    private Set<Category> missingCategories = new HashSet<Category>();
    private String dbUsername = "root";
    private String dbPassword = "root";
    private String dbConnection = "jdbc:mysql://localhost/ayn24shop_test?";
    
    public static void main(String... args) {
        TapirCategoryChecker c = new TapirCategoryChecker();
        // Read in data
        c.populateFromDb();
        // Add category parent object and child cats
        c.generateRelationships();
        // Collect category IDs that have a branch with more than one tapir_level
        c.findDoubles();
        // Work out if their siblings will show or not
        c.findParentOfDoubles();
        // Output cats
        c.outputResults();
    }
    
    /**
     * Work through every category populating children...
     */
    private void generateRelationships() {
        for (Category c : catmap.values()) {
            if (c.parentId != null && c.parentId != 0) {
                Category parent = catmap.get(c.parentId);
                c.parent = parent;
                parent.children.add(c);
            }
        }
    }
    
    /**
     * Record categories which have more than one tapir_level_flag on their branch.
     */
    private void findDoubles() {
        for (Category c : catmap.values()) {
            int count = countTapirLevelFlags(c);
            if (count > 1) {
                doubleTapirs.add(c);
            }
        }
    }
    
    /**
     * Count number of tapir_level_flags on the current category's branch.
     * @param c category
     * @return
     */
    private int countTapirLevelFlags(Category c) {
        int count = 0;
        while (true) {
            if (c.tapirLevelFlag) {
                count++;
            }
            if (c.parent != null) {
                c = c.parent;
            } else {
                break;
            }
        }
        return count;
    }
    
    /**
     * Find the flag parent of double Tapir flag categories we have recorded 
     */
    private void findParentOfDoubles() {
        for (Category cat : doubleTapirs) {
            // Find next flag up the branch
            Category parent = getParentWithFlag(cat.parentId);
            // check children that have no flag in their branch...
            parentsOfDoubleTapirs.add(parent);
        }
        // Now process the unique parents from set
        for (Category c : parentsOfDoubleTapirs) {
            checkSiblingNodes(c);
        }
    }
    
    /**
     * Check whether any sibling categories under the tapir flag parent don't have the flag set - they will disappear!
     * @param category
     */
    private void checkSiblingNodes(Category category) {
        Set<Category> nonFlaggedCats = new HashSet<Category>();
        for (Category child : category.children) {
            if (!child.tapirLevelFlag) {
                nonFlaggedCats.add(child);
            }
            // Recurse!
            checkSiblingNodes(child);
        }
        // Check if we have siblings with no tapir flag as well as at least one with.
        // Need to check that *all* non-flagged siblings have flagged children or they won't appear...
        if (nonFlaggedCats.size() < category.children.size()) {
            countChildFlags(nonFlaggedCats, 0);
        }
    }

    /**
     * Recursive method which counts category children to ensure all have tapir flags
     * @param cats
     * @param count
     * @return the total tapir flags
     */
    private int countChildFlags(Set<Category> cats, int count) {
        for (Category child : cats) {
            if (child.tapirLevelFlag) {
                count++;
            }
            if (child.children.size() > 0) {
                count += countChildFlags(child.children, count);
            }
            // No flags found - this category won't appear...
            if (count == 0) {
                missingCategories.add(child);
            }
        }
        return count;
    }
    
    /**
     * Return the breadcrumb for the current category.
     * @param cat category
     * @return Breadcrumb string
     */
    private String showBreadcrumb(Category cat) {
        StringBuilder sb = new StringBuilder();
        String joiner = "";
        while (true) {
            sb.insert(0, cat.name + (joiner.length()==0 ? " ("+cat.id+")" : joiner));
            if (cat.parent == null) {
                break;
            } else {
                cat = cat.parent;
            }
            joiner = " > ";
        }
        return sb.toString();
    }
    
    /**
     * Returns a string containing all tapir-flagged siblings of current category.
     * @param parent
     * @return
     */
    private String showTapirFlagSiblings(Category parent) {
        StringBuilder sb = new StringBuilder();
        String comma = "";
        for (Category cat : parent.parent.children) {
            if (cat.tapirLevelFlag) {
                sb.append(comma).append(cat.name).append(" (").append(cat.id).append(")");
                comma = ", ";
            }
        }
        if (sb.length() == 0) {
            if (parent.parent != null) {
                return showTapirFlagSiblings(parent.parent);
            }
        }
        return sb.toString();
    }
    
    /**
     * Finds parent tapir level flag category.
     * @param categoryId categoryId
     * @return parent category
     */
    private Category getParentWithFlag(Integer categoryId) {
        Category c = catmap.get(categoryId);
        while (true) {
            if (c.tapirLevelFlag) {
                return c;
            }
            if (c.parent != null) {
                c = c.parent;
            } else {
                break;
            }
        }
        return null;
    }
    
    /**
     * Sort results in category id order and outputs to stdout.
     */
    private void outputResults() {
        // sort map
        List<Category> list = new ArrayList<Category>(missingCategories);
        Collections.sort(list, (new Category()).CategoryIdComparator);
        for (Category cat : list) {
            System.out.println(showBreadcrumb(cat) + "\n        caused by: " + showTapirFlagSiblings(cat));
        }
    }
    
    /**
     * Adds category to catmap collection.
     * @param id the id
     * @param parent the parent id
     * @param name the name
     * @param tapirLevelValue tapir level value (0 or 1)
     */
    private void addToMap(Integer id, Integer parent, String name, Integer tapirLevelValue) {
        catmap.put(id, new Category(id, parent, name, tapirLevelValue));
    }
    
    /**
     * Read category data from db.
     */
    private void populateFromDb() {
        try {
            checkForExternalProps();
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            String connectionString = dbConnection + "&user=" + dbUsername + "&password=" + dbPassword;
            Connection connect = DriverManager
                .getConnection(connectionString);
            // Statements allow to issue SQL queries to the database
            Statement statement = connect.createStatement();
            // Result set get the result of the SQL query
            ResultSet resultSet = statement
                .executeQuery("select id, parent, name, tapir_level_flag from category where mapping_id=1 and deleted is false");
            while (resultSet.next()) {
                addToMap(resultSet.getInt("id"), resultSet.getInt("parent"), resultSet.getString("name"), resultSet.getInt("tapir_level_flag"));
            }
        } catch (Exception e) {
            System.err.println("Database access failed! " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Override default db properties with those in ayn-ds.xml if available.
     */
    private void checkForExternalProps() {
        // Try to acquire database connection string from ayn-ds.xml
        File f = new File("/opt/ayn/jboss/standalone/deployments/ayn-ds.xml");
        if (!f.exists()) {
            f = new File("C:\\Users\\colin.maclean\\dev\\apps\\jboss-as-7.1.1.Final\\standalone\\deployments\\ayn-ds.xml");
            if (!f.exists()) {
                return;
            }
        }
        try {
            // Parse ayn-ds.xml
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(f);
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            XPathExpression expr = xpath.compile("//datasources/datasource/connection-url");
            dbConnection = (String)expr.evaluate(doc, XPathConstants.STRING);
            expr = xpath.compile("//datasources/datasource/security/user-name");
            dbUsername = (String)expr.evaluate(doc, XPathConstants.STRING);
            expr = xpath.compile("//datasources/datasource/security/password");
            dbPassword = (String)expr.evaluate(doc, XPathConstants.STRING);
        } catch (Exception e) {
            System.err.println("Unable to parse file: " + f.getAbsolutePath());
            return;
        }
    }
        
    class Category {
        public Integer id;
        public Integer parentId;
        public Category parent;
        public String name;
        public boolean tapirLevelFlag;
        public Set<Category> children;
        
        public Category() {
            this.children = new HashSet<Category>();
        }
        
        public Category(Integer id, Integer parentId, String name, Integer tapirLevelValue) {
            this.id = id;
            this.parentId = parentId;
            this.name = name;
            this.tapirLevelFlag = tapirLevelValue == 1;
            this.children = new HashSet<Category>();
        }
        
        public final Comparator<Category> CategoryIdComparator = new Comparator<Category>() {
            public int compare(Category category1, Category category2) {
                return category1.id - (category2.id);
            }
        };
        
        @Override
        public String toString() {
            return "id:" + this.id + " name:" + this.name + "  children: " + this.children.size();
        }
    }
}
