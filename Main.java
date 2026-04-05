import pandas as pd

# ======================
# NODE
# ======================
class Node:
    def __init__(self, id, nama):
        self.id = id
        self.nama = nama
        self.left = None
        self.right = None

# ======================
# BST
# ======================
class BST:
    def __init__(self):
        self.root = None

    def insert(self, root, id, nama):
        if root is None:
            return Node(id, nama)

        if id < root.id:
            root.left = self.insert(root.left, id, nama)
        elif id > root.id:
            root.right = self.insert(root.right, id, nama)
        else:
            print("❌ ID sudah ada, tidak boleh duplikat!")

        return root

    def search(self, root, id):
        if root is None or root.id == id:
            return root

        if id < root.id:
            return self.search(root.left, id)
        return self.search(root.right, id)

    def min_value_node(self, root):
        while root.left:
            root = root.left
        return root

    def delete(self, root, id):
        if root is None:
            return root

        if id < root.id:
            root.left = self.delete(root.left, id)
        elif id > root.id:
            root.right = self.delete(root.right, id)
        else:
            if root.left is None:
                return root.right
            elif root.right is None:
                return root.left

            temp = self.min_value_node(root.right)
            root.id = temp.id
            root.nama = temp.nama
            root.right = self.delete(root.right, temp.id)

        return root

    def inorder(self, root):
        if root:
            self.inorder(root.left)
            print(root.id, "-", root.nama)
            self.inorder(root.right)

    def preorder(self, root):
        if root:
            print(root.id, "-", root.nama)
            self.preorder(root.left)
            self.preorder(root.right)

    def postorder(self, root):
        if root:
            self.postorder(root.left)
            self.postorder(root.right)
            print(root.id, "-", root.nama)

# ======================
# LOAD EXCEL
# ======================
def load_excel(path, bst):
    try:
        df = pd.read_excel(path)

        for _, row in df.iterrows():
            bst.root = bst.insert(bst.root, int(row['id']), str(row['nama']))

        print("✅ Data Excel berhasil dimuat!")

    except Exception as e:
        print("❌ Gagal membaca Excel:", e)

# ======================
# MAIN PROGRAM
# ======================
def main():
    bst = BST()

    while True:
        print("\n===== MENU BST =====")
        print("1. Tambah Data")
        print("2. Cari Data")
        print("3. Hapus Data")
        print("4. Traversal")
        print("5. Load dari Excel")
        print("6. Keluar")

        pilihan = input("Pilih menu: ")

        # TAMBAH
        if pilihan == "1":
            try:
                id = int(input("Masukkan ID: "))
                nama = input("Masukkan Nama: ")
                bst.root = bst.insert(bst.root, id, nama)
                print("✅ Data berhasil ditambahkan")
            except:
                print("❌ Input tidak valid")

        # CARI
        elif pilihan == "2":
            try:
                cari_id = int(input("Masukkan ID: "))
                hasil = bst.search(bst.root, cari_id)

                if hasil:
                    print("✅ Ditemukan:", hasil.id, "-", hasil.nama)
                else:
                    print("❌ Data tidak ditemukan")
            except:
                print("❌ Input tidak valid")

        # HAPUS
        elif pilihan == "3":
            try:
                hapus_id = int(input("Masukkan ID: "))
                bst.root = bst.delete(bst.root, hapus_id)
                print("✅ Data berhasil dihapus (jika ada)")
            except:
                print("❌ Input tidak valid")

        # TRAVERSAL
        elif pilihan == "4":
            if bst.root is None:
                print("❌ Tree masih kosong!")
                continue

            print("\n--- Traversal ---")
            print("1. Inorder")
            print("2. Preorder")
            print("3. Postorder")

            pilih_traversal = input("Pilih jenis traversal: ")

            if pilih_traversal == "1":
                print("\nInorder:")
                bst.inorder(bst.root)
            elif pilih_traversal == "2":
                print("\nPreorder:")
                bst.preorder(bst.root)
            elif pilih_traversal == "3":
                print("\nPostorder:")
                bst.postorder(bst.root)
            else:
                print("❌ Pilihan tidak valid")

        # LOAD EXCEL
        elif pilihan == "5":
            path = input("Masukkan path file Excel: ")
            load_excel(path, bst)

        # KELUAR
        elif pilihan == "6":
            print("Program selesai.")
            break

        else:
            print("❌ Pilihan tidak valid")

# ======================
# RUN
# ======================
if __name__ == "__main__":
    main()
    
