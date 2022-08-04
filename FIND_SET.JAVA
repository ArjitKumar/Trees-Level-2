import java.util.*;
public class FIND_SET{

    public static class TreeNode{
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        TreeNode(int val) {
            this.val = val;
        }
    }

    public static int size(TreeNode root) {
        return root == null ? 0 : size(root.left) + size(root.right) + 1;
    }
    public static int height(TreeNode root) {
        return root == null ? -1 : Math.max(height(root.left),height(root.right)) + 1;
    }

    public static int maximum(TreeNode root) {
        return root == null ? -(int)(1e9) : Math.max(root.val,Math.max(maximum(root.left),maximum(root.right)));
    }
    public static int minimum(TreeNode root) {
        return root == null ? (int)(1e9) : Math.min(root.val,Math.min(minimum(root.left),minimum(root.right)));
    }

    public static boolean find( TreeNode root, int data) {
        // BASE CASE
        if( root == null ) 
            return false;
        if( root.val == data) 
            return true;

        return find(root.left,data) || find(root.right,data);
    }

    // ROOT TO NODE PATH WITH ARRAYLIST AND WITH BOOL TYPE

    public static boolean nodeToRootPath_witBoolean(TreeNode root, int data, ArrayList<TreeNode> list) {
        if( root == null )
            return false;

        if( root.val == data) {
            list.add(root);
            return true;
        }
        boolean res = nodeToRootPath_witBoolean(root.left, data, list) || nodeToRootPath_witBoolean(root.right, data, list);
        // if done with if else
        // if( nodeToRootPath_witBoolean(root.left, data, list))
        //     list.add(root);
        // if( nodeToRootPath_witBoolean(root.right, data, list))
        //     list.add(root);
        
        // then no need to add res we simply return false and ignore the below code completly
        // but we can just do it with just one line
        if( res == true) {
            list.add(root);
        }
        
        return res;
    }

    public static ArrayList<TreeNode> nodeToRootPath(TreeNode root, int data) {
        if( root == null){
            return new ArrayList<TreeNode>();
        }
        if( root.val == data) {
            ArrayList<TreeNode> base = new ArrayList<>();
            base.add(root);
            return base;
        }
        ArrayList<TreeNode> left_list = nodeToRootPath(root.left, data);
        if( left_list.size() > 0) {
            left_list.add(root);
            return left_list;
        }
        ArrayList<TreeNode> right_list = nodeToRootPath(root.right, data);
        if( right_list.size() > 0) {
            right_list.add(root);
            return right_list;
        }
        return new ArrayList<TreeNode>();
    }
    

    // =======================================All leaf to root path ====================================================
     public static void AllrootToLeafPath(TreeNode root,ArrayList<ArrayList<Integer>> ans, ArrayList<Integer> smallAns ){
        if( root  == null ) return;
        if( root.left == null && root.right == null ){
            // Note we need to perform deep copy here wrna answer m sirf empty print hoga
            ArrayList<Integer> base = new ArrayList<>(smallAns);
            base.add(root.val); // is root ko to addd hi nhi kiya abhi tk
            ans.add(base);
            return;
        }
        // traverse the tree in preorder and add the value in smallans and remove the val in post order
        smallAns.add(root.val);
        AllrootToLeafPath(root.left,ans,smallAns);
        AllrootToLeafPath(root.right,ans,smallAns);
        smallAns.remove(smallAns.size()-1);
     }
    public static ArrayList<ArrayList<Integer>> AllrootToLeafPath(TreeNode root) {
        
        ArrayList<ArrayList<Integer>> ans = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> smallAns = new ArrayList<>();
        AllrootToLeafPath(root, ans, smallAns);
        for( ArrayList<Integer> list : ans) {
            for( int val : list) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
        return ans;
    }

    // ====================================== All single child parent in Binary tree ===========================

    public static void exactlyOneChild(TreeNode root, ArrayList<Integer> ans) {
        if( root == null || (root.left == null && root.right == null) ) return; // root null ho jae ya leaf ho jae 
        if( root.left == null && root.right != null || root.left != null && root.right == null){ // condition for single parent
             ans.add(root.val);
            /* Note do not use return statement wrna ans galat aeega  --------->      6
                                                                                     /
                                                                                    4
                                                                                    \
                                                                                     5  
                 is case m 6 se hi return kr jaega aur 4 aur 5 rehe jaenge isilye return nhi krenge backtrack hone ka mauka denge  */
        }
        exactlyOneChild(root.left,ans);
        exactlyOneChild(root.right,ans);
    }
    public static ArrayList<Integer> exactlyOneChild(TreeNode root) {
    ArrayList<Integer> ans = new ArrayList<>();
    exactlyOneChild(root, ans);
    for( int val : ans) {
        System.out.println(val + " ");
    }
    return ans;
  }


  // ================================================= Count Single Child Parent ========================================
  // we can use static variable, or above method and return arraylist size()
   public static int countExactlyOneChild(TreeNode root) {
    if( root == null || (root.left == null && root.right == null)) return 0; // root null ho jae ya leaf ho jae 
       
        int left = countExactlyOneChild(root.left);
        int right = countExactlyOneChild(root.right);
        int ans = left + right;
        // ye neeche wali condition agr preorder m check krli to ans galat aa jaeag
        // root bhi single child parent ho skta
        if(root.left == null || root.right == null) ans++;
        
        return ans;
  }
  // another way ( although both methods are exactly same this is with count variabel)
   public static int countExactlyOneChild_2(TreeNode root) {
    if( root == null || (root.left == null && root.right == null)) return 0; // root null ho jae ya leaf ho jae 
       
    int count = 0;
        count += countExactlyOneChild_2(root.left);
        count += countExactlyOneChild_2(root.right);
        // ye neeche wali condition agr preorder m check krli to ans galat aa jaeag
        // root bhi single child parent ho skta
        if(root.left == null || root.right == null) count++;
    
        return count;
  }

  //==========================================All Nodes K distance Far=======================================


//   LEETCODE 863

//   1. First apporach is the standart root_to node path approach 

// This is a better appraoch because we are not using any space apart from recursive space 
 
public static void printKlevelsDown(TreeNode root, int k, TreeNode block, List<Integer> list){
        if( root == null || k < 0 || root == block) return;
        if( k == 0) {
            list.add(root.val);
        }
        printKlevelsDown(root.left,k-1,block,list);
        printKlevelsDown(root.right,k-1,block,list);
        
    }
    public static int distanceK( TreeNode root, int target, int k , List<Integer> ans) {
        if( root == null) return -1;
        if( root.val == target) {
            printKlevelsDown(root,k-0,null,ans);
            return 1; // distanc from parent
        }
        
        int ld = distanceK(root.left, target, k, ans);
        if( ld != -1) {
            printKlevelsDown(root,k-ld,root.left,ans);
            return ld + 1;
        }
         int rd = distanceK(root.right, target, k, ans);
        if( rd != -1) {
            printKlevelsDown(root,k-rd,root.right,ans);
            return rd + 1;
        }
        
        return -1;
    }


    // ==================================== BURNING TREE =====================================
    // VERY IMPORTANT QUESTION FOR AMAZON 


    static int maxTime = 0;
  public static void burningTime(TreeNode root, TreeNode block, int time){
        if( root == null || root == block) return;
      
        maxTime = Math.max(time,maxTime);
        
        burningTime(root.left,block,time + 1);
        burningTime(root.right,block,time + 1);
        
    }
    
     public static int burningTree_( TreeNode root, int fireNode) {
        if( root == null) return -1;
        
        if( root.val == fireNode) {
           burningTime(root,null,0);
            return 1; 
        }
        
        int leftTime = burningTree_(root.left,fireNode);
        if( leftTime != -1) {
            burningTime(root, root.left,leftTime);
            return leftTime + 1;
        }
        int rightTime = burningTree_(root.right,fireNode);
        if( rightTime != -1) {
            burningTime(root,root.right,rightTime);
            return rightTime + 1;
        }
        
        return -1;
    }
   
  public static int burningTree(TreeNode root, int fireNode) {
     burningTree_(root,fireNode);
     return maxTime;
  }


//    FOLLOW UP :- PRINT THE BURNING SEQUENCE OF THE TREE
//    ANOTHER WAY TO SOLVE THE ABOVE QUESTION IS CONVERT THE TREE TO UNDIRECTED GRAPH THEN PRINT THE LEVEL ORDER TRAVERSAL 

 public static void burningTreeNode(TreeNode root, int time, TreeNode blockNode, ArrayList<ArrayList<Integer>> ans) {
        if (root == null || root == blockNode)
            return;
        if (time == ans.size()) // if(time == ans.size()) ans.push_back({});
            ans.add(new ArrayList<>());
        ans.get(time).add(root.val);
         

        burningTreeNode(root.left, time + 1, blockNode, ans);
        burningTreeNode(root.right, time + 1, blockNode, ans);

    }

    public static int burningTree2WithArrayList(TreeNode root, int fireNode, ArrayList<ArrayList<Integer>> ans) {
        if (root == null)
            return -1;
        if (root.val == fireNode) {
            burningTreeNode(root, 0, null, ans);
            return 1;
        }

        int lt = burningTree2WithArrayList(root.left, fireNode, ans);
        if (lt != -1) {
            burningTreeNode(root, lt, root.left, ans);
            return lt + 1;
        }

        int rt = burningTree2WithArrayList(root.right, fireNode, ans);
        if (rt != -1) {
            burningTreeNode(root, rt, root.right, ans);
            return rt + 1;
        }

        return -1;
    }

    public static void burningTree2(TreeNode root, int data) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        burningTree2WithArrayList(root, data, ans);
    }

//    LINK == https://www.geeksforgeeks.org/burn-the-binary-tree-starting-from-the-target-node/

//   ==============================================BURNING TREE ENDS =====================================
     public static TreeNode contstrucTree(TreeNode root){
        root = new TreeNode(12);
        root.left = new TreeNode(13);
        root.right = new TreeNode(10);
        root.right.left = new TreeNode(14);
        root.right.right = new TreeNode(15);
        TreeNode left = root.right.left;
        TreeNode right = root.right.right;
        left.left = new TreeNode(21);
        left.right = new TreeNode(24);
        right.left = new TreeNode(22);
        right.right = new TreeNode(23);
        return root;

                //         12
                //      /     \
                //    13       10
                //           /     \
                //        14       15
                //       /   \     /  \
                //      21   24   22   23

     }

    public static void main( String[] args) {
        TreeNode root = null;
        root = contstrucTree(root);  
        System.out.println(burningTree(root,14));  
        // burningTree2(root, 14);

    }
}
