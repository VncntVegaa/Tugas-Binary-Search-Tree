import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;

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
        left = right = null;
    }
}

// ======================
// BST
// ======================
class BST {
    Node root;

    // INSERT
    public Node insert(Node root, int id, String nama) {
        if (root == null) return new Node(id, nama);

        if (id < root.id) {
            root.left = insert(root.left, id, nama);
        } else if (id > root.id) {
            root.right = insert(root.right, id, nama);
        } else {
            System.out.println("❌ ID sudah ada!");
        }

        return root;
    }

    // SEARCH
    public Node search(Node root, int id) {
        if (root == null || root.id == id) return root;

        if (id < root.id) return search(root.left, id);
        return search(root.right, id);
    }

    // MIN VALUE
    public Node minValueNode(Node root) {
        while (root.left != null) {
            root = root.left;
        }
        return root;
    }

    // DELETE
    public Node delete(Node root, int id) {
        if (root == null) return null;

        if (id < root.id) {
            root.left = delete(root.left, id);
        } else if (id > root.id) {
            root.right = delete(root.right, id);
        } else {
            // 0 / 1 anak
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;

            // 2 anak
            Node temp = minValueNode(root.right);
            root.id = temp.id;
            root.nama = temp.nama;
            root.right = delete(root.right, temp.id);
        }
        return root;
    }

    // TRAVERSAL
    public void inorder(Node root) {
        if (root != null) {
            inorder(root.left);
            System.out.println(root.id + " - " + root.nama);
            inorder(root.right);
        }
    }

    public void preorder(Node root) {
        if (root != null) {
            System.out.println(root.id + " - " + root.nama);
            preorder(root.left);
            preorder(root.right);
        }
    }

    public void postorder(Node root) {
        if (root != null) {
            postorder(root.left);
            postorder(root.right);
            System.out.println(root.id + " - " + root.nama);
        }
    }
}

// ======================
// MAIN
// ======================
public class Main {

    // LOAD CSV
    public static void loadCSV(String filePath, BST bst) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            boolean header = true;

            while ((line = br.readLine()) != null) {
                if (header) {
                    header = false;
                    continue;
                }

                String[] data = line.split(",");

                int id = Integer.parseInt(data[0].trim());
                String nama = data[1].trim();

                bst.root = bst.insert(bst.root, id, nama);
            }

            br.close();
            System.out.println("✅ Data CSV berhasil dimuat!");

        } catch (Exception e) {
            System.out.println("❌ Gagal membaca file CSV!");
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        BST bst = new BST();

        while (true) {
            System.out.println("\n===== MENU BST =====");
            System.out.println("1. Tambah Data");
            System.out.println("2. Cari Data");
            System.out.println("3. Hapus Data");
            System.out.println("4. Traversal");
            System.out.println("5. Load dari CSV");
            System.out.println("6. Keluar");

            System.out.print("Pilih menu: ");
            String pilihan = input.nextLine();

            switch (pilihan) {

                case "1":
                    try {
                        System.out.print("Masukkan ID: ");
                        int id = Integer.parseInt(input.nextLine());

                        System.out.print("Masukkan Nama: ");
                        String nama = input.nextLine();

                        bst.root = bst.insert(bst.root, id, nama);
                        System.out.println("✅ Data ditambahkan");
                    } catch (Exception e) {
                        System.out.println("❌ Input tidak valid");
                    }
                    break;

                case "2":
                    try {
                        System.out.print("Masukkan ID: ");
                        int id = Integer.parseInt(input.nextLine());

                        Node hasil = bst.search(bst.root, id);

                        if (hasil != null) {
                            System.out.println("✅ Ditemukan: " + hasil.id + " - " + hasil.nama);
                        } else {
                            System.out.println("❌ Data tidak ditemukan");
                        }
                    } catch (Exception e) {
                        System.out.println("❌ Input tidak valid");
                    }
                    break;

                case "3":
                    try {
                        System.out.print("Masukkan ID: ");
                        int id = Integer.parseInt(input.nextLine());

                        bst.root = bst.delete(bst.root, id);
                        System.out.println("✅ Data dihapus (jika ada)");
                    } catch (Exception e) {
                        System.out.println("❌ Input tidak valid");
                    }
                    break;

                case "4":
                    if (bst.root == null) {
                        System.out.println("❌ Tree kosong!");
                        break;
                    }

                    System.out.println("\n1. Inorder");
                    System.out.println("2. Preorder");
                    System.out.println("3. Postorder");
                    System.out.print("Pilih: ");
                    String t = input.nextLine();

                    switch (t) {
                        case "1":
                            bst.inorder(bst.root);
                            break;
                        case "2":
                            bst.preorder(bst.root);
                            break;
                        case "3":
                            bst.postorder(bst.root);
                            break;
                        default:
                            System.out.println("❌ Pilihan tidak valid");
                    }
                    break;

                case "5":
                    System.out.print("Masukkan path file CSV: ");
                    String path = input.nextLine();
                    loadCSV(path, bst);
                    break;

                case "6":
                    System.out.println("Program selesai.");
                    input.close();
                    return;

                default:
                    System.out.println("❌ Pilihan tidak valid");
            }
        }
    }
}
