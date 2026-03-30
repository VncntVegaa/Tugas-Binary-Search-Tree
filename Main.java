import java.util.Scanner;

// ======================
// NODE
// ======================
class Node {
    int id;
    String nama;
    Node left, right;

    public Node(int id, String nama) {
        this.id = id;
        this.nama = nama;
        this.left = null;
        this.right = null;
    }
}

// ======================
// BST
// ======================
class BST {
    Node root;

    // INSERT
    public Node insert(Node root, int id, String nama) {
        if (root == null) {
            return new Node(id, nama);
        }

        if (id < root.id) {
            root.left = insert(root.left, id, nama);
        } else if (id > root.id) {
            root.right = insert(root.right, id, nama);
        } else {
            System.out.println("❌ ID sudah ada, tidak boleh duplikat!");
        }

        return root;
    }

    // SEARCH
    public Node search(Node root, int id) {
        if (root == null || root.id == id) {
            return root;
        }

        if (id < root.id) {
            return search(root.left, id);
        }
        return search(root.right, id);
    }

    // MIN VALUE NODE
    public Node minValueNode(Node root) {
        Node current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    // DELETE
    public Node delete(Node root, int id) {
        if (root == null) {
            return root;
        }

        if (id < root.id) {
            root.left = delete(root.left, id);
        } else if (id > root.id) {
            root.right = delete(root.right, id);
        } else {
            // 1 anak / tidak ada anak
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            // 2 anak
            Node temp = minValueNode(root.right);
            root.id = temp.id;
            root.nama = temp.nama;
            root.right = delete(root.right, temp.id);
        }

        return root;
    }

    // INORDER
    public void inorder(Node root) {
        if (root != null) {
            inorder(root.left);
            System.out.println(root.id + " - " + root.nama);
            inorder(root.right);
        }
    }

    // PREORDER
    public void preorder(Node root) {
        if (root != null) {
            System.out.println(root.id + " - " + root.nama);
            preorder(root.left);
            preorder(root.right);
        }
    }

    // POSTORDER
    public void postorder(Node root) {
        if (root != null) {
            postorder(root.left);
            postorder(root.right);
            System.out.println(root.id + " - " + root.nama);
        }
    }
}

// ======================
// MAIN PROGRAM
// ======================
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        BST bst = new BST();

        while (true) {
            System.out.println("\n===== MENU BST =====");
            System.out.println("1. Tambah Data");
            System.out.println("2. Cari Data");
            System.out.println("3. Hapus Data");
            System.out.println("4. Traversal");
            System.out.println("5. Keluar");

            System.out.print("Pilih menu: ");
            String pilihan = input.nextLine();

            // TAMBAH DATA
            if (pilihan.equals("1")) {
                try {
                    System.out.print("Masukkan ID: ");
                    int id = Integer.parseInt(input.nextLine());
                    System.out.print("Masukkan Nama: ");
                    String nama = input.nextLine();

                    bst.root = bst.insert(bst.root, id, nama);
                    System.out.println("✅ Data berhasil ditambahkan");
                } catch (Exception e) {
                    System.out.println("❌ Input tidak valid");
                }
            }

            // CARI DATA
            else if (pilihan.equals("2")) {
                try {
                    System.out.print("Masukkan ID: ");
                    int cariId = Integer.parseInt(input.nextLine());

                    Node hasil = bst.search(bst.root, cariId);

                    if (hasil != null) {
                        System.out.println("✅ Ditemukan: " + hasil.id + " - " + hasil.nama);
                    } else {
                        System.out.println("❌ Data tidak ditemukan");
                    }
                } catch (Exception e) {
                    System.out.println("❌ Input tidak valid");
                }
            }

            // HAPUS DATA
            else if (pilihan.equals("3")) {
                try {
                    System.out.print("Masukkan ID: ");
                    int hapusId = Integer.parseInt(input.nextLine());

                    bst.root = bst.delete(bst.root, hapusId);
                    System.out.println("✅ Data berhasil dihapus (jika ada)");
                } catch (Exception e) {
                    System.out.println("❌ Input tidak valid");
                }
            }

            // TRAVERSAL
            else if (pilihan.equals("4")) {
                if (bst.root == null) {
                    System.out.println("❌ Tree masih kosong!");
                    continue;
                }

                System.out.println("\n--- Traversal ---");
                System.out.println("1. Inorder");
                System.out.println("2. Preorder");
                System.out.println("3. Postorder");

                System.out.print("Pilih jenis traversal: ");
                String pilihTraversal = input.nextLine();

                if (pilihTraversal.equals("1")) {
                    System.out.println("\nInorder:");
                    bst.inorder(bst.root);
                } else if (pilihTraversal.equals("2")) {
                    System.out.println("\nPreorder:");
                    bst.preorder(bst.root);
                } else if (pilihTraversal.equals("3")) {
                    System.out.println("\nPostorder:");
                    bst.postorder(bst.root);
                } else {
                    System.out.println("❌ Pilihan tidak valid");
                }
            }

            // KELUAR
            else if (pilihan.equals("5")) {
                System.out.println("Program selesai.");
                break;
            }

            else {
                System.out.println("❌ Pilihan tidak valid");
            }
        }

        input.close();
    }
}