package com.zishi.algorithm.a07_tree;

public class Node {
    private int no;
    private String name;
    private Node left;
    private Node right;

    //构造方法
    public Node(int no, String name) {
        this.no = no;
        this.name = name;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "Node[ no = " + no + ", name =" + name + " ]";
    }

    // 前序遍历
    public void pre() {
        System.out.println(this);// 输出根节点
        // 左子树递归
        if (this.left != null) {
            this.left.pre();
        }
        // 右子树递归
        if (this.right != null) {
            this.right.pre();
        }
    }

    // 中序遍历
    public void mid() {
        // 左子树递归
        if (this.left != null) {
            this.left.mid();
        }
        System.out.println(this);// 输出根节点
        // 右子树递归
        if (this.right != null) {
            this.right.mid();
        }
    }

    // 后序遍历
    public void suf() {
        // 左子树递归
        if (this.left != null) {
            this.left.suf();
        }
        // 右子树递归
        if (this.right != null) {
            this.right.suf();
        }
        System.out.println(this);// 输出根节点
    }

    /**
     * 前序查找思路：
     * 先判断当前结点的no是否等于要查找的
     * 如果是相等，则返回当前结点
     * 如果不等，则判断当前结点的左子节点是否为空，如果不为空,则递归前序查找
     * 如果左递归前序查找，找到结点，则返回，否继续判断，
     * 当前的结点的右子节点是否为空,如果不空，则继续向右递归前序查找.
     *
     * @param no
     * @return
     */
    public Node preSearch(int no) {
        if (this.no == no) {
            return this;
        }
        Node resNode = null;
        if (this.left != null) {
            resNode = this.left.preSearch(no);
        }
        if (resNode != null) {
            return resNode;
        }
        if (this.right != null) {
            resNode = this.right.preSearch(no);
        }
        return resNode;
    }

    /**
     * 中序遍历查找思路
     * <p>
     * 判断当前结点的左子节点是否为空,如果不为空,则递归中序查找
     * 如果找到，则返回，如果没有找到，就和当前结点比较，
     * 如果是则返回当前结点，否则继续进行右递归的中序查找
     * 如果右递归中序查找，找到就返回，否则返回null
     *
     * @param no
     * @return
     */
    public Node midSearch(int no) {
        Node resNode = null;
        if (this.left != null) {
            resNode = this.left.midSearch(no);
        }
        if (resNode != null) {
            return resNode;
        }
        if (this.no == no) {
            return this;
        }
        if (this.right != null) {
            resNode = this.right.midSearch(no);
        }
        return resNode;
    }

    /**
     * 后序遍历查找思路
     * <p>
     * 判断当前结点的左子节点是否为空,如果不为空，则递归后序查找
     * 如果找到，就返回，如果没有找到，就判断当前结点的右子节点是否为空,
     * 如果不为空，则右递归进行后序查找，如果找到，就返回
     * 就和当前结点进行，比如，如果是则返回，否则返回ull
     */
    public Node sufSearch(int no) {
        Node resNode = null;
        if (this.left != null) {
            resNode = this.left.sufSearch(no);
        }
        if (resNode != null) {
            return resNode;
        }
        if (this.right != null) {
            resNode = this.right.sufSearch(no);
        }
        if (resNode != null) {
            return resNode;
        }
        if (this.no == no) {
            return this;
        }
        return resNode;
    }


    /**
     * 删除节点方法
     *规定
     * 如果删除的节点是叶子节点，则删除该节点
     * 如果删除的节点是非叶子节点，则删除该子树.
     *
     * 思路分析
     *     首先先处理：考虑如果树是空树root,如果只有- -个root结点，则等价将二叉树置空
     * 然后进行下面步骤
     *  1. 因为我们的二叉树是单向的，所以我们是判断当前结点的子结点是否需要删除结点，而不能去判断当前这个结点是不是需要删除结点
     *  2. 如果当前结点的左子结点不为空，并且左子结点就是要删除结点,就将this.left=null；并且就返回(结束递归删除)
     *  3. 如果当前结点的右子结点不为空，并且右子结点就是要删除结点，就将this.right=null；并且就返回(结束递归删除)
     *  4. 如果第2和第3步没有删除结点，那么我们就需要向左子树进行递归删除
     *  5. 如果第4步也没有删除结点,则应当向右子树进行递归删除.
     * @param no
     */
    public void delete(int no) {
        //从左节点开始判断是不是索要删除的节点。
        if (this.left != null && this.left.no == no) {
            this.left = null;
            return;
        }
        //随后同理,判断有节点是不是要删除的节点
        if (this.right != null && this.right.no == no) {
            this.right = null;
            return;
        }
        // 向左子树进行递归
        if (this.left != null) {
            this.left.delete(no);
        }
        // 向右子树递归
        if (this.right != null) {
            this.right.delete(no);
        }
    }

}
