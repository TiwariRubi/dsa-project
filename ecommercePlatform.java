

// code

import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// ==================== CUSTOM DATA STRUCTURES ====================

// Custom HashMap (using separate chaining)
class HashNode {
    String key;
    Object value;
    HashNode next;

    public HashNode(String key, Object value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }
}

class CustomHashMap {
    private HashNode[] buckets;
    private int capacity;
    private int size;

    public CustomHashMap() {
        capacity = 16;
        buckets = new HashNode[capacity];
        size = 0;
    }

    private int getBucketIndex(String key) {
       // int hashCode = key.hashCode();
       // return Math.abs(hashCode) % capacity;
        int hash = 0;
       // int capacity = 10;  // example, or your map’s bucket size

        // create our own hash value using character ASCII values
        for (int i = 0; i < key.length(); i++) {
            char ch = key.charAt(i);
            hash = (hash * 31 + ch);  // 31 is a prime number commonly used in hashing
        }

        if (hash < 0)
            hash = -hash;  // make sure it's positive

        return hash % capacity;  // ensure index is within bounds
    }

    public void put(String key, Object value) {
        int index = getBucketIndex(key);
        HashNode head = buckets[index];

        while (head != null) {
            if (head.key.equals(key)) {
                head.value = value;
                return;
            }
            head = head.next;
        }

        HashNode newNode = new HashNode(key, value);
        newNode.next = buckets[index];
        buckets[index] = newNode;
        size++;

        if ((1.0 * size) / capacity >= 0.7) {
            resize();
        }
    }

    public Object get(String key) {
        int index = getBucketIndex(key);
        HashNode head = buckets[index];

        while (head != null) {
            if (head.key.equals(key)) {
                return head.value;
            }
            head = head.next;
        }
        return null;
    }

    public boolean containsKey(String key) {
        return get(key) != null;
    }

    public void remove(String key) {
        int index = getBucketIndex(key);
        HashNode head = buckets[index];
        HashNode prev = null;

        while (head != null) {
            if (head.key.equals(key)) {
                if (prev == null) {
                    buckets[index] = head.next;
                } else {
                    prev.next = head.next;
                }
                size--;
                return;
            }
            prev = head;
            head = head.next;
        }
    }

    public ArrayList<String> getKeys() {
        ArrayList<String> keys = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            HashNode node = buckets[i];
            while (node != null) {
                keys.add(node.key);
                node = node.next;
            }
        }
        return keys;
    }

    public ArrayList<Object> getValues() {
        ArrayList<Object> values = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            HashNode node = buckets[i];
            while (node != null) {
                values.add(node.value);
                node = node.next;
            }
        }
        return values;
    }

    private void resize() {
        HashNode[] oldBuckets = buckets;
        capacity *= 2;
        buckets = new HashNode[capacity];
        size = 0;

        for (int i = 0; i < oldBuckets.length; i++) {
            HashNode node = oldBuckets[i];
            while (node != null) {
                put(node.key, node.value);
                node = node.next;
            }
        }
    }
}

// Custom Stack
class StackNode {
    Object data;
    StackNode next;

    public StackNode(Object data) {
        this.data = data;
        this.next = null;
    }
}

class CustomStack {
    private StackNode top;
    private int size;

    public CustomStack() {
        top = null;
        size = 0;
    }

    public void push(Object data) {
        StackNode newNode = new StackNode(data);
        newNode.next = top;
        top = newNode;
        size++;
    }

    public Object pop() {
        if (isEmpty()) {
            return null;
        }
        Object data = top.data;
        top = top.next;
        size--;
        return data;
    }

    public Object peek() {
        return isEmpty() ? null : top.data;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }
}

// Custom Queue
class QueueNode {
    Object data;
    QueueNode next;

    public QueueNode(Object data) {
        this.data = data;
        this.next = null;
    }
}

class CustomQueue {
    private QueueNode front;
    private QueueNode rear;
    private int size;

    public CustomQueue() {
        front = rear = null;
        size = 0;
    }

    public void enqueue(Object data) {
        QueueNode newNode = new QueueNode(data);
        if (rear == null) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    public Object dequeue() {
        if (isEmpty()) {
            return null;
        }
        Object data = front.data;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        size--;
        return data;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public int size() {
        return size;
    }
}

// Custom HashSet (using HashMap)
class CustomHashSet {
    private CustomHashMap map;
    private static final Object PRESENT = new Object();

    public CustomHashSet() {
        map = new CustomHashMap();
    }

    public void add(String element) {
        map.put(element, PRESENT);
    }

    public boolean contains(String element) {
        return map.containsKey(element);
    }

    public void remove(String element) {
        map.remove(element);
    }

    public boolean isEmpty() {
        return map.getKeys().isEmpty();
    }

    public ArrayList<String> toArray() {
        return map.getKeys();
    }
}

// Custom Priority Queue (Min Heap)
class PriorityQueueNode {
    Product product;
    double priority;

    public PriorityQueueNode(Product product, double priority) {
        this.product = product;
        this.priority = priority;
    }
}

class CustomPriorityQueue {
    private PriorityQueueNode[] heap;
    private int size;
    private int capacity;

    public CustomPriorityQueue() {
        capacity = 10;
        heap = new PriorityQueueNode[capacity];
        size = 0;
    }

    public void offer(Product product) {
        double priority = product.getRating() * product.getNumReviews();
        if (size == capacity) {
            resize();
        }
        heap[size] = new PriorityQueueNode(product, priority);
        heapifyUp(size);
        size++;
    }

    public Product poll() {
        if (isEmpty()) return null;
        Product result = heap[0].product;
        heap[0] = heap[--size];
        heapifyDown(0);
        return result;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void heapifyUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (heap[index].priority <= heap[parent].priority) break;
            swap(index, parent);
            index = parent;
        }
    }

    private void heapifyDown(int index) {
        while (true) {
            int largest = index;
            int left = 2 * index + 1;
            int right = 2 * index + 2;

            if (left < size && heap[left].priority > heap[largest].priority) {
                largest = left;
            }
            if (right < size && heap[right].priority > heap[largest].priority) {
                largest = right;
            }
            if (largest == index) break;

            swap(index, largest);
            index = largest;
        }
    }

    private void swap(int i, int j) {
        PriorityQueueNode temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    private void resize() {
        capacity *= 2;
        PriorityQueueNode[] newHeap = new PriorityQueueNode[capacity];
        for (int i = 0; i < size; i++) {
            newHeap[i] = heap[i];
        }
        heap = newHeap;
    }

    public CustomPriorityQueue copy() {
        CustomPriorityQueue newQueue = new CustomPriorityQueue();
        for (int i = 0; i < size; i++) {
            newQueue.offer(heap[i].product);
        }
        return newQueue;
    }
}

// Custom BST for sorted products
class BSTNode {
    String key;
    Product product;
    BSTNode left, right;

    public BSTNode(String key, Product product) {
        this.key = key;
        this.product = product;
        this.left = this.right = null;
    }
}

class CustomBST {
    private BSTNode root;

    public CustomBST() {
        root = null;
    }

    public void insert(String key, Product product) {
        root = insertRec(root, key, product);
    }

    private BSTNode insertRec(BSTNode root, String key, Product product) {
        if (root == null) {
            return new BSTNode(key, product);
        }
        if (key.compareTo(root.key) < 0) {
            root.left = insertRec(root.left, key, product);
        } else if (key.compareTo(root.key) > 0) {
            root.right = insertRec(root.right, key, product);
        }
        return root;
    }

    public ArrayList<Product> inorderTraversal() {
        ArrayList<Product> result = new ArrayList<>();
        inorderRec(root, result);
        return result;
    }

    private void inorderRec(BSTNode root, ArrayList<Product> result) {
        if (root != null) {
            inorderRec(root.left, result);
            result.add(root.product);
            inorderRec(root.right, result);
        }
    }
}

// Custom LRU Cache (Recently Viewed)
class LRUNode {
    String key;
    Object value;
    LRUNode prev, next;

    public LRUNode(String key, Object value) {
        this.key = key;
        this.value = value;
    }
}

class CustomLRUCache {
    private CustomHashMap map;
    private LRUNode head, tail;
    private int capacity;
    private int size;

    public CustomLRUCache(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        map = new CustomHashMap();
        head = new LRUNode("", null);
        tail = new LRUNode("", null);
        head.next = tail;
        tail.prev = head;
    }

    public void put(String key, Object value) {
        if (map.containsKey(key)) {
            LRUNode node = (LRUNode) map.get(key);
            removeNode(node);
            addToFront(node);
        } else {
            if (size == capacity) {
                LRUNode lru = tail.prev;
                removeNode(lru);
                map.remove(lru.key);
                size--;
            }
            LRUNode newNode = new LRUNode(key, value);
            map.put(key, newNode);
            addToFront(newNode);
            size++;
        }
    }

    public ArrayList<String> getKeys() {
        ArrayList<String> keys = new ArrayList<>();
        LRUNode current = head.next;
        while (current != tail) {
            keys.add(current.key);
            current = current.next;
        }
        return keys;
    }

    private void addToFront(LRUNode node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(LRUNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}

// ==================== PRODUCT CLASSES ====================
class Product {
    private String id;
    private String name;
    private String category;
    private double price;
    private int stock;
    private double rating;
    private int numReviews;
    private String description;
    private String seller;
    private ArrayList<Review> reviews;

    public Product(String id, String name, String category, double price, int stock, String description, String seller) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.seller = seller;
        this.rating = 0.0;
        this.numReviews = 0;
        this.reviews = new ArrayList<>();
    }

    public void addReview(Review review) {
        reviews.add(review);
        numReviews++;
        double sum = 0;
        for (Review r : reviews) {
            sum += r.getRating();
        }
        rating = sum / numReviews;
    }

    public boolean reduceStock(int quantity) {
        if (stock >= quantity) {
            stock -= quantity;
            return true;
        }
        return false;
    }

    public void increaseStock(int quantity) {
        stock += quantity;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public double getRating() { return rating; }
    public int getNumReviews() { return numReviews; }
    public String getDescription() { return description; }
    public String getSeller() { return seller; }
    public ArrayList<Review> getReviews() { return reviews; }

    public String toString() {
        return String.format("[%s] %s - ₹%.2f (★%.1f) - Stock: %d", id, name, price, rating, stock);
    }
}

class Review {
    private String userId;
    private String userName;
    private int rating;
    private String comment;
    private LocalDateTime date;

    public Review(String userId, String userName, int rating, String comment) {
        this.userId = userId;
        this.userName = userName;
        this.rating = rating;
        this.comment = comment;
        this.date = LocalDateTime.now();
    }

    public int getRating() { return rating; }
    public String getUserName() { return userName; }
    public String getComment() { return comment; }
    public LocalDateTime getDate() { return date; }
}

// ==================== CART & WISHLIST ====================
class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getTotalPrice() { return product.getPrice() * quantity; }
}

// ==================== ORDER CLASSES ====================
class Order {
    private String orderId;
    private String userId;
    private ArrayList<OrderItem> items;
    private double totalAmount;
    private String status;
    private LocalDateTime orderDate;
    private Address shippingAddress;
    private String paymentMethod;

    public Order(String orderId, String userId, ArrayList<OrderItem> items, double totalAmount,
                 Address shippingAddress, String paymentMethod) {
        this.orderId = orderId;
        this.userId = userId;
        this.items = items;
        this.totalAmount = totalAmount;
        this.status = "PENDING";
        this.orderDate = LocalDateTime.now();
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
    }

    public String getOrderId() { return orderId; }
    public ArrayList<OrderItem> getItems() { return items; }
    public double getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public Address getShippingAddress() { return shippingAddress; }

    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return String.format("Order #%s | Date: %s | Total: ₹%.2f | Status: %s",
                orderId, orderDate.format(formatter), totalAmount, status);
    }
}

class OrderItem {
    private Product product;
    private int quantity;
    private double priceAtOrder;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.priceAtOrder = product.getPrice();
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public double getPriceAtOrder() { return priceAtOrder; }
    public double getTotalPrice() { return priceAtOrder * quantity; }
}

// ==================== USER CLASSES ====================
class User {
    private String userId;
    private String username;
    private String password;
    private String email;
    private String phone;
    private ArrayList<Address> addresses;
    private CustomHashMap cart;
    private CustomHashSet wishlist;
    private ArrayList<Order> orderHistory;
    private double walletBalance;

    public User(String userId, String username, String password, String email, String phone) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.addresses = new ArrayList<>();
        this.cart = new CustomHashMap();
        this.wishlist = new CustomHashSet();
        this.orderHistory = new ArrayList<>();
        this.walletBalance = 10000.0;
    }

    public void addToCart(Product product, int quantity) {
        if (cart.containsKey(product.getId())) {
            CartItem item = (CartItem) cart.get(product.getId());
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            cart.put(product.getId(), new CartItem(product, quantity));
        }
    }

    public void removeFromCart(String productId) {
        cart.remove(productId);
    }

    public void addToWishlist(String productId) {
        wishlist.add(productId);
    }

    public void removeFromWishlist(String productId) {
        wishlist.remove(productId);
    }

    public void addOrder(Order order) {
        orderHistory.add(order);
    }

    public boolean deductFromWallet(double amount) {
        if (walletBalance >= amount) {
            walletBalance -= amount;
            return true;
        }
        return false;
    }

    public void addToWallet(double amount) {
        walletBalance += amount;
    }

    public String getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public CustomHashMap getCart() { return cart; }
    public CustomHashSet getWishlist() { return wishlist; }
    public ArrayList<Order> getOrderHistory() { return orderHistory; }
    public ArrayList<Address> getAddresses() { return addresses; }
    public double getWalletBalance() { return walletBalance; }
}

class Address {
    private String name;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String phone;

    public Address(String name, String street, String city, String state, String zipCode, String phone) {
        this.name = name;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.phone = phone;
    }

    public String toString() {
        return String.format("%s, %s, %s, %s - %s (Ph: %s)", name, street, city, state, zipCode, phone);
    }
}

// ==================== MAIN SYSTEM ====================
class ECommerceSystem {
    private CustomHashMap users;
    private CustomHashMap products;
    private CustomHashMap categoryIndex;
    private CustomPriorityQueue trendingProducts;
    private CustomStack recentSearches;
    private CustomQueue orderQueue;
    private CustomBST sortedProducts;
    private CustomLRUCache recentlyViewed;
    private CustomHashMap orders;

    private User currentUser;
    private Scanner scanner;
    private int orderCounter;

    public ECommerceSystem() {
        users = new CustomHashMap();
        products = new CustomHashMap();
        categoryIndex = new CustomHashMap();
        trendingProducts = new CustomPriorityQueue();
        recentSearches = new CustomStack();
        orderQueue = new CustomQueue();
        sortedProducts = new CustomBST();
        recentlyViewed = new CustomLRUCache(10);
        orders = new CustomHashMap();
        scanner = new Scanner(System.in);
        orderCounter = 1000;

        initializeSampleData();
    }

    private void initializeSampleData() {
        // Electronics
        addProduct(new Product("P001", "iPhone 15 Pro", "Electronics", 134900.00, 50, "Latest Apple smartphone with A17 Pro chip", "Apple Store"));
        addProduct(new Product("P002", "Samsung Galaxy S24 Ultra", "Electronics", 129999.00, 45, "Premium Android flagship phone", "Samsung Official"));
        addProduct(new Product("P003", "Sony WH-1000XM5", "Electronics", 29990.00, 30, "Industry-leading noise cancelling headphones", "Sony Store"));
        addProduct(new Product("P004", "MacBook Air M2", "Electronics", 114900.00, 25, "Thin and light laptop with M2 chip", "Apple Store"));
        addProduct(new Product("P005", "iPad Pro 12.9", "Electronics", 112900.00, 20, "Powerful tablet with M2 chip", "Apple Store"));
        addProduct(new Product("P006", "Dell XPS 15", "Electronics", 159990.00, 15, "Premium Windows laptop", "Dell Official"));
        addProduct(new Product("P007", "PlayStation 5", "Electronics", 54990.00, 20, "Next-gen gaming console", "Sony Store"));
        addProduct(new Product("P008", "Nintendo Switch OLED", "Electronics", 37999.00, 35, "Portable gaming console", "Nintendo Store"));
        addProduct(new Product("P009", "Bose QuietComfort 45", "Electronics", 32900.00, 40, "Premium noise cancelling headphones", "Bose Official"));
        addProduct(new Product("P010", "JBL Flip 6", "Electronics", 12999.00, 60, "Portable Bluetooth speaker", "JBL Store"));

        // Fashion - Men
        addProduct(new Product("P011", "Levi's 511 Slim Jeans", "Fashion", 3499.00, 100, "Classic slim fit denim jeans", "Levi's Store"));
        addProduct(new Product("P012", "Nike Dri-FIT T-Shirt", "Fashion", 1799.00, 150, "Performance sports t-shirt", "Nike Official"));
        addProduct(new Product("P013", "Adidas Hoodie", "Fashion", 3999.00, 80, "Comfortable cotton hoodie", "Adidas Store"));
        addProduct(new Product("P014", "Puma Track Pants", "Fashion", 2499.00, 120, "Athletic track pants", "Puma Official"));
        addProduct(new Product("P015", "Allen Solly Formal Shirt", "Fashion", 1999.00, 90, "Professional formal shirt", "Allen Solly"));

        // Fashion - Women
        addProduct(new Product("P016", "Zara Floral Dress", "Fashion", 4999.00, 70, "Elegant floral summer dress", "Zara India"));
        addProduct(new Product("P017", "H&M Skinny Jeans", "Fashion", 2499.00, 85, "Trendy skinny fit jeans", "H&M Store"));
        addProduct(new Product("P018", "Forever 21 Crop Top", "Fashion", 899.00, 120, "Stylish crop top", "Forever 21"));
        addProduct(new Product("P019", "Biba Ethnic Kurti", "Fashion", 1799.00, 95, "Traditional Indian kurti", "Biba"));
        addProduct(new Product("P020", "FabIndia Cotton Saree", "Fashion", 3499.00, 40, "Pure cotton handloom saree", "FabIndia"));

        // Shoes
        addProduct(new Product("P021", "Nike Air Max 270", "Shoes", 12995.00, 100, "Comfortable running shoes with air cushioning", "Nike Official"));
        addProduct(new Product("P022", "Adidas Ultraboost 22", "Shoes", 16999.00, 75, "Premium running shoes", "Adidas Store"));
        addProduct(new Product("P023", "Puma RS-X", "Shoes", 8999.00, 90, "Retro style sneakers", "Puma Official"));
        addProduct(new Product("P024", "Woodland Leather Boots", "Shoes", 5999.00, 60, "Durable leather boots", "Woodland"));
        addProduct(new Product("P025", "Bata Formal Shoes", "Shoes", 2999.00, 110, "Classic formal leather shoes", "Bata Store"));
        addProduct(new Product("P026", "Crocs Classic Clogs", "Shoes", 2499.00, 130, "Comfortable casual clogs", "Crocs Official"));

        // Grocery
        addProduct(new Product("P027", "Tata Salt", "Grocery", 22.00, 500, "Iodized salt 1kg pack", "Tata"));
        addProduct(new Product("P028", "Fortune Sunflower Oil", "Grocery", 185.00, 300, "Refined sunflower oil 1L", "Fortune"));
        addProduct(new Product("P029", "India Gate Basmati Rice", "Grocery", 899.00, 200, "Premium basmati rice 5kg", "India Gate"));
        addProduct(new Product("P030", "Amul Butter", "Grocery", 56.00, 250, "Fresh salted butter 100g", "Amul"));
        addProduct(new Product("P031", "Nestle Maggi Noodles", "Grocery", 144.00, 400, "Instant noodles 12-pack", "Nestle"));
        addProduct(new Product("P032", "Britannia Good Day Cookies", "Grocery", 55.00, 350, "Butter cookies 150g", "Britannia"));
        addProduct(new Product("P033", "Nescafe Classic Coffee", "Grocery", 349.00, 180, "Instant coffee 100g", "Nescafe"));
        addProduct(new Product("P034", "Red Label Tea", "Grocery", 265.00, 220, "Premium tea leaves 500g", "Red Label"));

        // Stationery
        addProduct(new Product("P035", "Classmate Notebook", "Stationery", 85.00, 300, "Single ruled notebook 172 pages", "Classmate"));
        addProduct(new Product("P036", "Parker Jotter Pen", "Stationery", 299.00, 150, "Premium ballpoint pen", "Parker"));
        addProduct(new Product("P037", "Apsara Platinum Pencils", "Stationery", 60.00, 250, "Extra dark pencils pack of 10", "Apsara"));
        addProduct(new Product("P038", "Fevicol Glue", "Stationery", 45.00, 200, "All purpose adhesive 100ml", "Fevicol"));
        addProduct(new Product("P039", "Camlin Acrylic Colors", "Stationery", 499.00, 80, "Artist quality paints set", "Camlin"));
        addProduct(new Product("P040", "HP Printer Paper", "Stationery", 349.00, 120, "A4 white paper 500 sheets", "HP"));

        // Beauty & Skincare
        addProduct(new Product("P041", "Lakme Eyeconic Kajal", "Beauty", 199.00, 200, "Smudge-proof kajal", "Lakme"));
        addProduct(new Product("P042", "Maybelline Fit Me Foundation", "Beauty", 499.00, 150, "Matte + poreless foundation", "Maybelline"));
        addProduct(new Product("P043", "Biotique Bio Almond Oil", "Skincare", 299.00, 180, "Nourishing baby massage oil", "Biotique"));
        addProduct(new Product("P044", "Himalaya Neem Face Wash", "Skincare", 175.00, 250, "Purifying face wash 150ml", "Himalaya"));
        addProduct(new Product("P045", "Nivea Soft Cream", "Skincare", 249.00, 220, "Light moisturizing cream", "Nivea"));
        addProduct(new Product("P046", "Lotus Herbals Sunscreen", "Skincare", 450.00, 160, "SPF 50 sun protection", "Lotus"));
        addProduct(new Product("P047", "The Body Shop Tea Tree Oil", "Skincare", 1095.00, 100, "Purifying facial oil 30ml", "The Body Shop"));
        addProduct(new Product("P048", "Forest Essentials Face Serum", "Skincare", 2795.00, 70, "Luxury Ayurvedic serum", "Forest Essentials"));

        // Kitchenware
        addProduct(new Product("P049", "Prestige Pressure Cooker", "Kitchenware", 1899.00, 80, "Aluminum pressure cooker 5L", "Prestige"));
        addProduct(new Product("P050", "Hawkins Futura Non-Stick Pan", "Kitchenware", 1599.00, 90, "Hard anodized frying pan", "Hawkins"));
        addProduct(new Product("P051", "Wonderchef Gas Stove", "Kitchenware", 4999.00, 50, "3 burner glass top stove", "Wonderchef"));
        addProduct(new Product("P052", "Pigeon Electric Kettle", "Kitchenware", 899.00, 120, "1.5L stainless steel kettle", "Pigeon"));
        addProduct(new Product("P053", "Milton Casserole Set", "Kitchenware", 1799.00, 100, "3-piece insulated casserole", "Milton"));
        addProduct(new Product("P054", "Cello Storage Containers", "Kitchenware", 599.00, 150, "Airtight container set of 6", "Cello"));
        addProduct(new Product("P055", "Borosil Mixing Bowl Set", "Kitchenware", 799.00, 110, "Glass mixing bowls 5-piece", "Borosil"));
        addProduct(new Product("P056", "Butterfly Mixer Grinder", "Kitchenware", 3999.00, 65, "750W mixer grinder with jars", "Butterfly"));

        // Cleaning Essentials
        addProduct(new Product("P057", "Vim Dishwash Liquid", "Cleaning", 199.00, 300, "Lemon liquid gel 2L", "Vim"));
        addProduct(new Product("P058", "Lizol Floor Cleaner", "Cleaning", 249.00, 250, "Disinfectant surface cleaner 975ml", "Lizol"));
        addProduct(new Product("P059", "Harpic Toilet Cleaner", "Cleaning", 179.00, 280, "Power plus toilet cleaner 1L", "Harpic"));
        addProduct(new Product("P060", "Surf Excel Detergent", "Cleaning", 449.00, 200, "Matic front load 2kg", "Surf Excel"));
        addProduct(new Product("P061", "Colin Glass Cleaner", "Cleaning", 149.00, 220, "Streak-free shine 500ml", "Colin"));
        addProduct(new Product("P062", "Scotch Brite Scrub Pad", "Cleaning", 89.00, 350, "Large scrub sponge 3-pack", "Scotch Brite"));
        addProduct(new Product("P063", "Savlon Surface Disinfectant", "Cleaning", 229.00, 180, "Multi-purpose spray 500ml", "Savlon"));

        // Home Decor
        addProduct(new Product("P064", "IKEA Photo Frame Set", "Home Decor", 999.00, 120, "Wall picture frames 5-piece", "IKEA"));
        addProduct(new Product("P065", "eCraftIndia Wall Clock", "Home Decor", 1499.00, 90, "Designer analog wall clock", "eCraftIndia"));
        addProduct(new Product("P066", "Urban Ladder Table Lamp", "Home Decor", 2499.00, 70, "Modern bedside lamp", "Urban Ladder"));
        addProduct(new Product("P067", "HomeCenter Cushion Covers", "Home Decor", 799.00, 150, "Cotton cushion covers set of 5", "HomeCenter"));
        addProduct(new Product("P068", "Cortina Door Curtains", "Home Decor", 1299.00, 100, "Blackout curtains 7ft", "Cortina"));
        addProduct(new Product("P069", "Saaj Designer Vase", "Home Decor", 899.00, 85, "Ceramic flower vase", "Saaj"));
        addProduct(new Product("P070", "Bombay Dyeing Bedsheet", "Home Decor", 1999.00, 110, "Cotton double bedsheet with pillows", "Bombay Dyeing"));
        addProduct(new Product("P071", "Athom Living Wall Art", "Home Decor", 1799.00, 95, "Canvas painting abstract", "Athom Living"));
        addProduct(new Product("P072", "Wakefit Orthopaedic Pillow", "Home Decor", 799.00, 130, "Memory foam pillow", "Wakefit"));

        // Books
        addProduct(new Product("P073", "The Great Gatsby", "Books", 299.00, 200, "Classic novel by F. Scott Fitzgerald", "Penguin"));
        addProduct(new Product("P074", "1984 by George Orwell", "Books", 350.00, 180, "Dystopian fiction masterpiece", "Penguin"));
        addProduct(new Product("P075", "The Alchemist", "Books", 399.00, 160, "Paulo Coelho's bestseller", "HarperCollins"));
        addProduct(new Product("P076", "Rich Dad Poor Dad", "Books", 399.00, 140, "Financial education book", "Plata Publishing"));
        addProduct(new Product("P077", "Atomic Habits", "Books", 599.00, 120, "James Clear's self-help guide", "Penguin"));

        User sampleUser = new User("U001", "john_doe", "password123", "john@email.com", "9876543210");
        sampleUser.getAddresses().add(new Address("John Doe", "123 MG Road", "Mumbai", "Maharashtra", "400001", "9876543210"));
        users.put(sampleUser.getUsername(), sampleUser);
    }

    private void addProduct(Product product) {
        products.put(product.getId(), product);
        sortedProducts.insert(product.getName(), product);

        if (!categoryIndex.containsKey(product.getCategory())) {
            categoryIndex.put(product.getCategory(), new ArrayList<Product>());
        }
        ArrayList<Product> categoryList = (ArrayList<Product>) categoryIndex.get(product.getCategory());
        categoryList.add(product);

        trendingProducts.offer(product);
    }

    public void start() {

        System.out.println("  Welcome to ShopEasy E-Commerce Store     ");
        System.out.println("       Your One-Stop Shop in India        ");
        System.out.println();


        while (true) {
            if (currentUser == null) {
                showLoginMenu();
            } else {
                showMainMenu();
            }
        }
    }

    private void showLoginMenu() {
        System.out.println("\n1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Choose option: ");

        int choice = getIntInput();

        switch (choice) {
            case 1: login(); break;
            case 2: register(); break;
            case 3:
                System.out.println("Thank you for visiting ShopEasy!");
                System.exit(0);
            default: System.out.println("Invalid option!");
        }
    }

    private void login() {
        System.out.print("\nUsername: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = (User) users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            System.out.println("\nLogin successful! Welcome, " + username + "!");
        } else {
            System.out.println("\n Invalid credentials!");
        }
    }

    private void register() {
        System.out.print("\nUsername: ");
        String username = scanner.nextLine();

        if (users.containsKey(username)) {
            System.out.println("Username already exists!");
            return;
        }

        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();


        while (email == null || email.trim().isEmpty() || !email.contains("@") || !email.contains(".") || email.length() < 5) {
            System.out.println("Please enter valid email");
            email = scanner.nextLine();
        }

        while (phone == null || phone.length() != 10) {
            System.out.println("Please enter valid phone number");
            phone = scanner.nextLine();

        }



        String userId = "U" + String.format("%03d", users.getKeys().size() + 1);
        User newUser = new User(userId, username, password, email, phone);
        users.put(username, newUser);

        System.out.println("\n Registration successful! Please login.");
    }

    private void showMainMenu() {

        System.out.println("            MAIN MENU                     ");
        System.out.println("1.  Browse Products                        ");
        System.out.println("2.  Search Products                        ");
        System.out.println("3.  Browse by Category                     ");
        System.out.println("4.  View Trending Products                 ");
        System.out.println("5.  View Cart                              ");
        System.out.println("6.  View Wishlist                          ");
        System.out.println("7.  My Orders                              ");
        System.out.println("8.  Recently Viewed                        ");
        System.out.println("9.  Search History                         ");
        System.out.println("10. Manage Addresses                       ");
        System.out.println("11. Wallet (Balance: ₹" + String.format("%.2f", currentUser.getWalletBalance()) + ")      ");
        System.out.println("12. Logout                                 ");

        System.out.print("Choose option: ");

        int choice = getIntInput();

        switch (choice) {
            case 1: browseProducts(); break;
            case 2: searchProducts(); break;
            case 3: browseByCategory(); break;
            case 4: viewTrendingProducts(); break;
            case 5: viewCart(); break;
            case 6: viewWishlist(); break;
            case 7: viewOrders(); break;
            case 8: viewRecentlyViewed(); break;
            case 9: viewSearchHistory(); break;
            case 10: manageAddresses(); break;
            case 11: manageWallet(); break;
            case 12: logout(); break;
            default: System.out.println("Invalid option!");
        }
    }

    private void browseProducts() {
        System.out.println("\n=== ALL PRODUCTS ===");
        ArrayList<Product> productList = sortedProducts.inorderTraversal();

        for (int i = 0; i < productList.size(); i++) {
            Product product = productList.get(i);
            System.out.println((i + 1) + ". " + product);
        }

        System.out.print("\nEnter product number to view details (0 to go back): ");
        int choice = getIntInput();

        if (choice > 0 && choice <= productList.size()) {
            viewProductDetails(productList.get(choice - 1));
        }
    }

    private void searchProducts() {
        System.out.print("\nEnter search term: ");
        String searchTerm = scanner.nextLine().toLowerCase();
        recentSearches.push(searchTerm);

        ArrayList<Product> results = new ArrayList<>();
        ArrayList<Object> allProducts = products.getValues();

        for (Object obj : allProducts) {
            Product product = (Product) obj;
            if (product.getName().toLowerCase().contains(searchTerm) ||
                    product.getDescription().toLowerCase().contains(searchTerm) ||
                    product.getCategory().toLowerCase().contains(searchTerm)) {
                results.add(product);
            }
        }

        if (results.isEmpty()) {
            System.out.println("No products found!");
            return;
        }

        System.out.println("\n=== SEARCH RESULTS ===");
        for (int i = 0; i < results.size(); i++) {
            System.out.println((i + 1) + ". " + results.get(i));
        }

        System.out.print("\nEnter product number to view details (0 to go back): ");
        int choice = getIntInput();

        if (choice > 0 && choice <= results.size()) {
            viewProductDetails(results.get(choice - 1));
        }
    }

    private void browseByCategory() {
        System.out.println("\n=== CATEGORIES ===");
        ArrayList<String> categories = categoryIndex.getKeys();

        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i));
        }

        System.out.print("\nChoose category: ");
        int choice = getIntInput();

        if (choice > 0 && choice <= categories.size()) {
            String category = categories.get(choice - 1);
            ArrayList<Product> categoryProducts = (ArrayList<Product>) categoryIndex.get(category);

            System.out.println("\n=== " + category.toUpperCase() + " ===");
            for (int i = 0; i < categoryProducts.size(); i++) {
                System.out.println((i + 1) + ". " + categoryProducts.get(i));
            }

            System.out.print("\nEnter product number to view details (0 to go back): ");
            int prodChoice = getIntInput();

            if (prodChoice > 0 && prodChoice <= categoryProducts.size()) {
                viewProductDetails(categoryProducts.get(prodChoice - 1));
            }
        }
    }

    private void viewTrendingProducts() {
        System.out.println("\n=== TRENDING PRODUCTS ===");
        CustomPriorityQueue tempQueue = trendingProducts.copy();
        ArrayList<Product> trending = new ArrayList<>();

        int count = 0;
        while (!tempQueue.isEmpty() && count < 5) {
            trending.add(tempQueue.poll());
            count++;
        }

        for (int i = 0; i < trending.size(); i++) {
            System.out.println((i + 1) + ". " + trending.get(i));
        }

        System.out.print("\nEnter product number to view details (0 to go back): ");
        int choice = getIntInput();

        if (choice > 0 && choice <= trending.size()) {
            viewProductDetails(trending.get(choice - 1));
        }
    }

    private void viewProductDetails(Product product) {
        recentlyViewed.put(product.getId(), product);


        System.out.println("  " + product.getName());
        System.out.println("Product ID: " + product.getId());
        System.out.println("Category: " + product.getCategory());
        System.out.println("Price: Rs" + String.format("%.2f", product.getPrice()));
        System.out.println("Rating: *" + String.format("%.1f", product.getRating()) + " (" + product.getNumReviews() + " reviews)");
        System.out.println("Stock: " + product.getStock());
        System.out.println("Seller: " + product.getSeller());
        System.out.println("Description: " + product.getDescription());

        ArrayList<Review> reviews = product.getReviews();
        if (!reviews.isEmpty()) {
            System.out.println("\n--- Recent Reviews ---");
            int count = 0;
            for (int i = 0; i < reviews.size() && count < 3; i++, count++) {
                Review review = reviews.get(i);
                System.out.println("*" + review.getRating() + " by " + review.getUserName() + ": " + review.getComment());
            }
        }

        System.out.println("\n1. Add to Cart");
        System.out.println("2. Add to Wishlist");
        System.out.println("3. Write a Review");
        System.out.println("4. Back");
        System.out.print("Choose option: ");

        int choice = getIntInput();

        switch (choice) {
            case 1:
                System.out.print("Enter quantity: ");
                int qty = getIntInput();
                if (qty > 0 && qty <= product.getStock()) {
                    currentUser.addToCart(product, qty);
                    System.out.println(" Added to cart!");
                } else {
                    System.out.println(" Invalid quantity or insufficient stock!");
                }
                break;
            case 2:
                currentUser.addToWishlist(product.getId());
                System.out.println("Added to wishlist!");
                break;
            case 3:
                writeReview(product);
                break;
            default :
                System.out.println("invalid option ");
        }
    }

    private void writeReview(Product product) {
        System.out.print("Rating (1-5): ");
        int rating = getIntInput();
        if (rating < 1 || rating > 5) {
            System.out.println("Invalid rating!");
            return;
        }

        System.out.print("Comment: ");
        String comment = scanner.nextLine();

        Review review = new Review(currentUser.getUserId(), currentUser.getUsername(), rating, comment);
        product.addReview(review);
        System.out.println(" Review submitted!");
    }

    private void viewCart() {
        CustomHashMap cart = currentUser.getCart();
        ArrayList<String> productIds = cart.getKeys();

        if (productIds.isEmpty()) {
            System.out.println("\nYour cart is empty!");
            return;
        }

        System.out.println("\n=== YOUR CART ===");
        double total = 0;

        for (int i = 0; i < productIds.size(); i++) {
            String productId = productIds.get(i);
            CartItem item = (CartItem) cart.get(productId);
            System.out.println((i + 1) + ". " + item.getProduct().getName() +
                    " - Qty: " + item.getQuantity() +
                    " - Rs" + String.format("%.2f", item.getTotalPrice()));
            total += item.getTotalPrice();
        }

        System.out.println("\nTotal: Rs" + String.format("%.2f", total));
        System.out.println("\n1. Proceed to Checkout");
        System.out.println("2. Remove Item");
        System.out.println("3. Back");
        System.out.print("Choose option: ");

        int choice = getIntInput();

        switch (choice) {
            case 1: checkout(); break;
            case 2:
                System.out.print("Enter item number to remove: ");
                int itemNum = getIntInput();
                if (itemNum > 0 && itemNum <= productIds.size()) {
                    currentUser.removeFromCart(productIds.get(itemNum - 1));
                    System.out.println(" Item removed!");
                }
                break;
            default :
                System.out.println("invalid option");
        }
    }

    private void checkout() {
        CustomHashMap cart = currentUser.getCart();
        ArrayList<String> productIds = cart.getKeys();
        if (productIds.isEmpty()) return;

        ArrayList<Address> addresses = currentUser.getAddresses();
        if (addresses.isEmpty()) {
            System.out.println("Please add a delivery address first!");
            addAddress();
            if (addresses.isEmpty()) return;
        }

        System.out.println("\n=== SELECT DELIVERY ADDRESS ===");
        for (int i = 0; i < addresses.size(); i++) {
            System.out.println((i + 1) + ". " + addresses.get(i));
        }
        System.out.print("Choose address: ");
        int addrChoice = getIntInput();

        if (addrChoice < 1 || addrChoice > addresses.size()) return;
        Address selectedAddress = addresses.get(addrChoice - 1);

        System.out.println("\n=== PAYMENT METHOD ===");
        System.out.println("1. Wallet (Balance: ₹" + String.format("%.2f", currentUser.getWalletBalance()) + ")");
        System.out.println("2. Credit/Debit Card");
        System.out.println("3. Cash on Delivery");
        System.out.print("Choose payment method: ");

        int paymentChoice = getIntInput();
        String paymentMethod = "";

        switch (paymentChoice) {
            case 1: paymentMethod = "Wallet"; break;
            case 2: paymentMethod = "Card"; break;
            case 3: paymentMethod = "COD"; break;
            default: return;
        }

        double total = 0;
        for (String productId : productIds) {
            CartItem item = (CartItem) cart.get(productId);
            total += item.getTotalPrice();
        }

        if (paymentMethod.equals("Wallet")) {
            if (!currentUser.deductFromWallet(total)) {
                System.out.println(" Insufficient wallet balance!");
                return;
            }
        }

        ArrayList<OrderItem> orderItems = new ArrayList<>();
        for (String productId : productIds) {
            CartItem cartItem = (CartItem) cart.get(productId);
            if (!cartItem.getProduct().reduceStock(cartItem.getQuantity())) {
                System.out.println(" Insufficient stock for " + cartItem.getProduct().getName());
                return;
            }
            orderItems.add(new OrderItem(cartItem.getProduct(), cartItem.getQuantity()));
        }

        String orderId = "ORD" + (orderCounter++);
        Order order = new Order(orderId, currentUser.getUserId(), orderItems, total, selectedAddress, paymentMethod);
        currentUser.addOrder(order);
        orders.put(orderId, order);
        orderQueue.enqueue(order);

        // Clear cart
        for (String productId : productIds) {
            cart.remove(productId);
        }

        System.out.println("\n Order placed successfully!");
        System.out.println("Order ID: " + orderId);
        System.out.println("Total Amount: Rs" + String.format("%.2f", total));
    }

    private void viewWishlist() {
        CustomHashSet wishlist = currentUser.getWishlist();
        ArrayList<String> productIds = wishlist.toArray();

        if (productIds.isEmpty()) {
            System.out.println("\nYour wishlist is empty!");
            return;
        }

        System.out.println("\n=== YOUR WISHLIST ===");

        for (int i = 0; i < productIds.size(); i++) {
            Product product = (Product) products.get(productIds.get(i));
            if (product != null) {
                System.out.println((i + 1) + ". " + product);
            }
        }

        System.out.print("\nEnter product number to view details (0 to go back): ");
        int choice = getIntInput();

        if (choice > 0 && choice <= productIds.size()) {
            viewProductDetails((Product) products.get(productIds.get(choice - 1)));
        }
    }

    private void viewOrders() {
        ArrayList<Order> orderHistory = currentUser.getOrderHistory();

        if (orderHistory.isEmpty()) {
            System.out.println("\nNo orders yet!");
            return;
        }

        System.out.println("\n=== YOUR ORDERS ===");
        for (int i = 0; i < orderHistory.size(); i++) {
            System.out.println((i + 1) + ". " + orderHistory.get(i));
        }

        System.out.print("\nEnter order number to view details (0 to go back): ");
        int choice = getIntInput();

        if (choice > 0 && choice <= orderHistory.size()) {
            viewOrderDetails(orderHistory.get(choice - 1));
        }
    }

    private void viewOrderDetails(Order order) {
        System.out.println("\n=== ORDER DETAILS ===");
        System.out.println("Order ID: " + order.getOrderId());
        System.out.println("Status: " + order.getStatus());
        System.out.println("Order Date: " + order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        System.out.println("Delivery Address: " + order.getShippingAddress());
        System.out.println("\nItems:");

        ArrayList<OrderItem> items = order.getItems();
        for (OrderItem item : items) {
            System.out.println("  - " + item.getProduct().getName() +
                    " x " + item.getQuantity() +
                    " = Rs" + String.format("%.2f", item.getTotalPrice()));
        }

        System.out.println("\nTotal Amount: Rs" + String.format("%.2f", order.getTotalAmount()));

        if (order.getStatus().equals("PENDING") || order.getStatus().equals("CONFIRMED")) {
            System.out.println("\n1. Cancel Order");
            System.out.println("2. Back");
            System.out.print("Choose option: ");

            int choice = getIntInput();
            if (choice == 1) {
                order.setStatus("CANCELLED");
                currentUser.addToWallet(order.getTotalAmount());

                for (OrderItem item : items) {
                    item.getProduct().increaseStock(item.getQuantity());
                }
                System.out.println(" Order cancelled! Amount refunded to wallet.");
            }
        }
    }

    private void viewRecentlyViewed() {
        if (recentlyViewed.isEmpty()) {
            System.out.println("\nNo recently viewed items!");
            return;
        }

        System.out.println("\n=== RECENTLY VIEWED ===");
        ArrayList<String> productIds = recentlyViewed.getKeys();

        for (int i = 0; i < productIds.size(); i++) {
            Product product = (Product) products.get(productIds.get(i));
            if (product != null) {
                System.out.println((i + 1) + ". " + product);
            }
        }

        System.out.print("\nEnter product number to view details (0 to go back): ");
        int choice = getIntInput();

        if (choice > 0 && choice <= productIds.size()) {
            viewProductDetails((Product) products.get(productIds.get(choice - 1)));
        }
    }

    private void viewSearchHistory() {
        if (recentSearches.isEmpty()) {
            System.out.println("\nNo search history!");
            return;
        }

        System.out.println("\n=== SEARCH HISTORY ===");
        CustomStack tempStack = new CustomStack();
        int count = 1;

        while (!recentSearches.isEmpty() && count <= 10) {
            String search = (String) recentSearches.pop();
            tempStack.push(search);
            System.out.println(count++ + ". " + search);
        }

        while (!tempStack.isEmpty()) {
            recentSearches.push(tempStack.pop());
        }

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private void manageAddresses() {
        System.out.println("\n=== MANAGE ADDRESSES ===");
        System.out.println("1. View Addresses");
        System.out.println("2. Add Address");
        System.out.println("3. Remove Address");
        System.out.println("4. Back");
        System.out.print("Choose option: ");

        int choice = getIntInput();

        switch (choice) {
            case 1: viewAddresses(); break;
            case 2: addAddress(); break;
            case 3: removeAddress(); break;

        }
    }

    private void viewAddresses() {
        ArrayList<Address> addresses = currentUser.getAddresses();

        if (addresses.isEmpty()) {
            System.out.println("\nNo addresses saved!");
            return;
        }

        System.out.println("\n=== YOUR ADDRESSES ===");
        for (int i = 0; i < addresses.size(); i++) {
            System.out.println((i + 1) + ". " + addresses.get(i));
        }

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private void addAddress() {
        System.out.println("\n=== ADD NEW ADDRESS ===");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Street: ");
        String street = scanner.nextLine();
        System.out.print("City: ");
        String city = scanner.nextLine();
        System.out.print("State: ");
        String state = scanner.nextLine();
        System.out.print("PIN Code: ");
        String zip = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();

        Address address = new Address(name, street, city, state, zip, phone);
        currentUser.getAddresses().add(address);
        System.out.println("Address added successfully!");
    }

    private void removeAddress() {
        ArrayList<Address> addresses = currentUser.getAddresses();

        if (addresses.isEmpty()) {
            System.out.println("\nNo addresses to remove!");
            return;
        }

        System.out.println("\n=== REMOVE ADDRESS ===");
        for (int i = 0; i < addresses.size(); i++) {
            System.out.println((i + 1) + ". " + addresses.get(i));
        }

        System.out.print("Enter address number to remove: ");
        int choice = getIntInput();

        if (choice > 0 && choice <= addresses.size()) {
            addresses.remove(choice - 1);
            System.out.println(" Address removed!");
        }
    }

    private void manageWallet() {
        System.out.println("\n=== WALLET ===");
        System.out.println("Current Balance: Rs" + String.format("%.2f", currentUser.getWalletBalance()));
        System.out.println("\n1. Add Money");
        System.out.println("2. Back");
        System.out.print("Choose option: ");

        int choice = getIntInput();

        if (choice == 1) {
            System.out.print("Enter amount to add: Rs");
            double amount = getDoubleInput();
            if (amount > 0) {
                currentUser.addToWallet(amount);
                System.out.println("Rs" + String.format("%.2f", amount) + " added to wallet!");
                System.out.println("New Balance: Rs" + String.format("%.2f", currentUser.getWalletBalance()));
            }
        }
    }

    private void logout() {
        currentUser = null;
        System.out.println("\n Logged out successfully!");
    }

    private int getIntInput() {
        try {
            int value = scanner.nextInt();
            scanner.nextLine();
            return value;
        } catch (Exception e) {
            scanner.nextLine();
            return -1;
        }
    }

    private double getDoubleInput() {
        try {
            double value = scanner.nextDouble();
            scanner.nextLine();
            return value;
        } catch (Exception e) {
            scanner.nextLine();
            return -1;
        }
    }
}

// ==================== MAIN CLASS ====================
public class ecommercePlatform {
    public static void main(String[] args) {
        try {
            ECommerceSystem system = new ECommerceSystem();
            system.start();
        }catch(Exception e){
            System.out.println("INVALID INPUT");
        }
    }

}
