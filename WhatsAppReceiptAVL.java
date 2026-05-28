public class WhatsAppReceiptAVL {

    static class Node {
        long timestamp;
        Node left, right;
        int height;

        Node(long timestamp) {
            this.timestamp = timestamp;
            this.height = 1;
        }
    }

    Node root;

    int height(Node node) {
        if (node == null)
            return 0;
        return node.height;
    }

    int max(int a, int b) {
        return (a > b) ? a : b;
    }

    int getBalance(Node node) {
        if (node == null)
            return 0;

        return height(node.left) - height(node.right);
    }

    Node rightRotate(Node y) {

        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = max(height(y.left),
                       height(y.right)) + 1;

        x.height = max(height(x.left),
                       height(x.right)) + 1;

        return x;
    }

    Node leftRotate(Node x) {

        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = max(height(x.left),
                       height(x.right)) + 1;

        y.height = max(height(y.left),
                       height(y.right)) + 1;

        return y;
    }

    Node insert(Node node, long timestamp) {

        if (node == null)
            return new Node(timestamp);

        if (timestamp < node.timestamp)
            node.left = insert(node.left, timestamp);

        else if (timestamp > node.timestamp)
            node.right = insert(node.right, timestamp);

        else
            return node;

        node.height =
            1 + max(height(node.left),
                    height(node.right));

        int balance = getBalance(node);

        // Left Left
        if (balance > 1 &&
            timestamp < node.left.timestamp)
            return rightRotate(node);

        // Right Right
        if (balance < -1 &&
            timestamp > node.right.timestamp)
            return leftRotate(node);

        // Left Right
        if (balance > 1 &&
            timestamp > node.left.timestamp) {

            node.left = leftRotate(node.left);

            return rightRotate(node);
        }

        // Right Left
        if (balance < -1 &&
            timestamp < node.right.timestamp) {

            node.right =
                rightRotate(node.right);

            return leftRotate(node);
        }

        return node;
    }

    long findOldestReceipt(Node node) {

        if (node == null)
            return -1;

        while (node.left != null)
            node = node.left;

        return node.timestamp;
    }

    void inorder(Node node) {

        if (node != null) {

            inorder(node.left);

            System.out.print(
                node.timestamp + " "
            );

            inorder(node.right);
        }
    }

    public static void main(String[] args) {

        WhatsAppReceiptAVL tree =
            new WhatsAppReceiptAVL();

        long receipts[] = {
            1001,1002,1003,
            1004,1005,1006,
            1007
        };

        for(long receipt : receipts) {

            tree.root =
                tree.insert(
                    tree.root,
                    receipt
                );
        }

        System.out.println(
            "Receipt timestamps:"
        );

        tree.inorder(tree.root);

        System.out.println();

        System.out.println(
            "Oldest Pending Receipt: "
            + tree.findOldestReceipt(
                tree.root
              )
        );
    }
}