// CouponPro Frontend Application
const API_BASE_URL = '/api';

// Global variables
let currentCart = { items: [] };
let allCoupons = [];
let demoMode = false;

// Initialize the application
document.addEventListener('DOMContentLoaded', function() {
    initializeApp();
});

function initializeApp() {
    // Set up event listeners
    setupEventListeners();

    // Load initial data
    loadSystemStatus();
    loadStats();

    // Show hero section by default
    showHeroSection();

    // Add scroll effects
    setupScrollEffects();
}

function setupScrollEffects() {
    window.addEventListener('scroll', function() {
        const nav = document.querySelector('.hero-nav');
        if (window.scrollY > 50) {
            nav.style.background = 'rgba(255, 255, 255, 0.98)';
            nav.style.boxShadow = '0 2px 20px rgba(0,0,0,0.1)';
        } else {
            nav.style.background = 'rgba(255, 255, 255, 0.95)';
            nav.style.boxShadow = 'none';
        }
    });
}

function showHeroSection() {
    // Hide all content sections
    const sections = document.querySelectorAll('.content-section');
    sections.forEach(section => {
        section.style.display = 'none';
    });

    // Show hero section
    const heroSection = document.querySelector('.hero-section');
    if (heroSection) {
        heroSection.style.display = 'flex';
    }

    // Update navigation
    updateNavigation('dashboard');
}

function setupEventListeners() {
    // Coupon type change handler
    document.getElementById('coupon-type').addEventListener('change', function() {
        showCouponTypeFields(this.value);
    });
    
    // Form submission
    document.getElementById('coupon-form').addEventListener('submit', function(e) {
        e.preventDefault();
        createCoupon();
    });
}

// Navigation Functions
function showSection(sectionName) {
    // Hide hero section
    const heroSection = document.querySelector('.hero-section');
    if (heroSection) {
        heroSection.style.display = 'none';
    }

    // Hide all content sections
    const sections = document.querySelectorAll('.content-section');
    sections.forEach(section => {
        section.style.display = 'none';
        section.classList.remove('active');
    });

    // Show main content container
    const mainContent = document.querySelector('.main-content');
    if (mainContent) {
        mainContent.style.display = 'block';
    }

    // Show selected section
    const targetSection = document.getElementById(sectionName + '-section');
    if (targetSection) {
        targetSection.style.display = 'block';
        targetSection.classList.add('active');
    }

    // Special case for dashboard - show hero if dashboard
    if (sectionName === 'dashboard') {
        if (heroSection) {
            heroSection.style.display = 'flex';
        }
        if (mainContent) {
            mainContent.style.display = 'block';
        }
        if (targetSection) {
            targetSection.style.display = 'block';
        }
    }

    // Update navigation
    updateNavigation(sectionName);

    // Load section-specific data
    switch(sectionName) {
        case 'dashboard':
            loadStats();
            loadSystemStatus();
            break;
        case 'demo':
            initializeDemo();
            break;
        case 'coupons':
            loadCoupons();
            break;
        case 'test-cart':
            initializeCart();
            break;
    }
}

function updateNavigation(activeSection) {
    const navLinks = document.querySelectorAll('.nav-link');
    navLinks.forEach(link => {
        link.classList.remove('active');
    });
    
    // Add active class to current section
    const activeLink = document.querySelector(`[href="#${activeSection}"]`);
    if (activeLink) {
        activeLink.classList.add('active');
    }
}

// System Status Functions
async function loadSystemStatus() {
    try {
        const response = await fetch(`${API_BASE_URL}/health`);
        const data = await response.json();
        
        const statusHtml = `
            <div class="d-flex align-items-center mb-2">
                <i class="fas fa-circle text-success me-2"></i>
                <span class="fw-bold">System Online</span>
            </div>
            <small class="text-muted">
                Service: ${data.service || 'CouponSystem'}<br>
                Version: ${data.version || '1.0.0'}<br>
                Active Coupons: ${data.activeCoupons || 0}
            </small>
        `;
        
        document.getElementById('system-status').innerHTML = statusHtml;
    } catch (error) {
        document.getElementById('system-status').innerHTML = `
            <div class="d-flex align-items-center mb-2">
                <i class="fas fa-circle text-danger me-2"></i>
                <span class="fw-bold">System Offline</span>
            </div>
            <small class="text-muted">Unable to connect to API</small>
        `;
    }
}

async function loadStats() {
    try {
        const response = await fetch(`${API_BASE_URL}/stats`);
        const data = await response.json();
        
        document.getElementById('total-coupons').textContent = data.totalCoupons || 0;
        document.getElementById('active-coupons').textContent = data.activeCoupons || 0;
        document.getElementById('cart-wise-coupons').textContent = data.typeBreakdown?.['cart-wise'] || 0;
        document.getElementById('product-wise-coupons').textContent = data.typeBreakdown?.['product-wise'] || 0;
    } catch (error) {
        console.error('Error loading stats:', error);
    }
}

async function checkHealth() {
    showToast('Checking system health...', 'info');
    await loadSystemStatus();
    await loadStats();
    showToast('System health check completed!', 'success');
}

// Coupon Management Functions
function showCouponTypeFields(type) {
    // Hide all type-specific fields
    const typeFields = document.querySelectorAll('.coupon-type-fields');
    typeFields.forEach(field => {
        field.style.display = 'none';
    });
    
    // Show selected type fields
    if (type) {
        const targetFields = document.getElementById(type + '-fields');
        if (targetFields) {
            targetFields.style.display = 'block';
        }
    }
}

async function createCoupon() {
    const type = document.getElementById('coupon-type').value;
    const description = document.getElementById('description').value;
    const expirationDate = document.getElementById('expiration-date').value;
    
    let couponData = {
        type: type,
        description: description,
        isActive: true
    };
    
    if (expirationDate) {
        couponData.expirationDate = expirationDate;
    }
    
    // Add type-specific fields
    switch(type) {
        case 'cart-wise':
            couponData.threshold = parseFloat(document.getElementById('threshold').value);
            couponData.discount = parseFloat(document.getElementById('cart-discount').value);
            couponData.discountType = document.getElementById('cart-discount-type').value;
            break;
            
        case 'product-wise':
            couponData.productId = parseInt(document.getElementById('product-id').value);
            couponData.discount = parseFloat(document.getElementById('product-discount').value);
            couponData.discountType = document.getElementById('product-discount-type').value;
            break;
            
        case 'bxgy':
            try {
                couponData.buyProducts = JSON.parse(document.getElementById('buy-products').value);
                couponData.getProducts = JSON.parse(document.getElementById('get-products').value);
                couponData.repetitionLimit = parseInt(document.getElementById('repetition-limit').value);
            } catch (error) {
                showToast('Invalid JSON format in buy/get products', 'error');
                return;
            }
            break;
    }
    
    try {
        const response = await fetch(`${API_BASE_URL}/coupons`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(couponData)
        });
        
        if (response.ok) {
            const createdCoupon = await response.json();
            showToast('Coupon created successfully!', 'success');
            document.getElementById('coupon-form').reset();
            showCouponTypeFields(''); // Hide type fields
            
            // Refresh stats
            loadStats();
        } else {
            const error = await response.json();
            showToast(`Error creating coupon: ${error.message}`, 'error');
        }
    } catch (error) {
        showToast('Network error. Please try again.', 'error');
        console.error('Error creating coupon:', error);
    }
}

async function loadCoupons() {
    const tableContainer = document.getElementById('coupons-table');
    tableContainer.innerHTML = `
        <div class="text-center">
            <div class="spinner-border" role="status"></div>
            <p class="mt-2">Loading coupons...</p>
        </div>
    `;
    
    try {
        const response = await fetch(`${API_BASE_URL}/coupons`);
        const coupons = await response.json();
        allCoupons = coupons;
        
        if (coupons.length === 0) {
            tableContainer.innerHTML = `
                <div class="text-center text-muted">
                    <i class="fas fa-ticket-alt fa-3x mb-3"></i>
                    <h5>No Coupons Found</h5>
                    <p>Create your first coupon to get started!</p>
                    <button class="btn btn-primary" onclick="showSection('create-coupon')">
                        <i class="fas fa-plus me-2"></i>Create Coupon
                    </button>
                </div>
            `;
            return;
        }
        
        let tableHtml = `
            <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Type</th>
                            <th>Description</th>
                            <th>Details</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
        `;
        
        coupons.forEach(coupon => {
            const details = getCouponDetails(coupon);
            const status = getCouponStatus(coupon);
            
            tableHtml += `
                <tr>
                    <td><span class="badge bg-secondary">${coupon.id}</span></td>
                    <td><span class="coupon-type-badge bg-primary text-white">${coupon.type}</span></td>
                    <td>${coupon.description}</td>
                    <td>${details}</td>
                    <td>${status}</td>
                    <td>
                        <button class="btn btn-sm btn-outline-danger" onclick="deleteCoupon(${coupon.id})">
                            <i class="fas fa-trash"></i>
                        </button>
                    </td>
                </tr>
            `;
        });
        
        tableHtml += `
                    </tbody>
                </table>
            </div>
        `;
        
        tableContainer.innerHTML = tableHtml;
    } catch (error) {
        tableContainer.innerHTML = `
            <div class="alert alert-danger">
                <i class="fas fa-exclamation-triangle me-2"></i>
                Error loading coupons. Please try again.
            </div>
        `;
        console.error('Error loading coupons:', error);
    }
}

function getCouponDetails(coupon) {
    switch(coupon.type) {
        case 'cart-wise':
            return `Threshold: $${coupon.threshold}, Discount: ${coupon.discount}${coupon.discountType === 'PERCENTAGE' ? '%' : '$'}`;
        case 'product-wise':
            return `Product: ${coupon.productId}, Discount: ${coupon.discount}${coupon.discountType === 'PERCENTAGE' ? '%' : '$'}`;
        case 'bxgy':
            return `Buy: ${coupon.buyProducts?.length || 0} products, Get: ${coupon.getProducts?.length || 0} products`;
        default:
            return 'N/A';
    }
}

function getCouponStatus(coupon) {
    const now = new Date();
    const expiration = coupon.expirationDate ? new Date(coupon.expirationDate) : null;
    
    if (!coupon.isActive) {
        return '<span class="badge bg-secondary">Inactive</span>';
    } else if (expiration && now > expiration) {
        return '<span class="badge bg-warning">Expired</span>';
    } else {
        return '<span class="badge bg-success">Active</span>';
    }
}

async function deleteCoupon(couponId) {
    if (!confirm('Are you sure you want to delete this coupon?')) {
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE_URL}/coupons/${couponId}`, {
            method: 'DELETE'
        });
        
        if (response.ok) {
            showToast('Coupon deleted successfully!', 'success');
            loadCoupons();
            loadStats();
        } else {
            showToast('Error deleting coupon', 'error');
        }
    } catch (error) {
        showToast('Network error. Please try again.', 'error');
        console.error('Error deleting coupon:', error);
    }
}

// Cart Management Functions
function initializeCart() {
    currentCart = { items: [] };
    updateCartDisplay();
}

function addCartItem() {
    const cartItemsContainer = document.getElementById('cart-items');
    const itemIndex = currentCart.items.length;
    
    const itemHtml = `
        <div class="cart-item" data-index="${itemIndex}">
            <div class="row">
                <div class="col-md-3">
                    <label class="form-label">Product ID</label>
                    <input type="number" class="form-control" placeholder="Product ID" onchange="updateCartItem(${itemIndex}, 'product_id', this.value)">
                </div>
                <div class="col-md-3">
                    <label class="form-label">Quantity</label>
                    <input type="number" class="form-control" placeholder="Quantity" min="1" onchange="updateCartItem(${itemIndex}, 'quantity', this.value)">
                </div>
                <div class="col-md-3">
                    <label class="form-label">Price</label>
                    <input type="number" class="form-control" placeholder="Price" step="0.01" onchange="updateCartItem(${itemIndex}, 'price', this.value)">
                </div>
                <div class="col-md-3">
                    <label class="form-label">Action</label>
                    <button class="btn btn-danger btn-sm d-block" onclick="removeCartItem(${itemIndex})">
                        <i class="fas fa-trash"></i> Remove
                    </button>
                </div>
            </div>
        </div>
    `;
    
    cartItemsContainer.insertAdjacentHTML('beforeend', itemHtml);
    
    // Add empty item to cart
    currentCart.items.push({
        product_id: null,
        quantity: null,
        price: null
    });
}

function updateCartItem(index, field, value) {
    if (currentCart.items[index]) {
        if (field === 'product_id') {
            currentCart.items[index].product_id = parseInt(value) || null;
        } else if (field === 'quantity') {
            currentCart.items[index].quantity = parseInt(value) || null;
        } else if (field === 'price') {
            currentCart.items[index].price = parseFloat(value) || null;
        }
        updateCartSummary();
    }
}

function removeCartItem(index) {
    currentCart.items.splice(index, 1);
    updateCartDisplay();
}

function updateCartDisplay() {
    const cartItemsContainer = document.getElementById('cart-items');
    cartItemsContainer.innerHTML = '';
    
    currentCart.items.forEach((item, index) => {
        const itemHtml = `
            <div class="cart-item" data-index="${index}">
                <div class="row">
                    <div class="col-md-3">
                        <label class="form-label">Product ID</label>
                        <input type="number" class="form-control" value="${item.product_id || ''}" placeholder="Product ID" onchange="updateCartItem(${index}, 'product_id', this.value)">
                    </div>
                    <div class="col-md-3">
                        <label class="form-label">Quantity</label>
                        <input type="number" class="form-control" value="${item.quantity || ''}" placeholder="Quantity" min="1" onchange="updateCartItem(${index}, 'quantity', this.value)">
                    </div>
                    <div class="col-md-3">
                        <label class="form-label">Price</label>
                        <input type="number" class="form-control" value="${item.price || ''}" placeholder="Price" step="0.01" onchange="updateCartItem(${index}, 'price', this.value)">
                    </div>
                    <div class="col-md-3">
                        <label class="form-label">Action</label>
                        <button class="btn btn-danger btn-sm d-block" onclick="removeCartItem(${index})">
                            <i class="fas fa-trash"></i> Remove
                        </button>
                    </div>
                </div>
            </div>
        `;
        cartItemsContainer.insertAdjacentHTML('beforeend', itemHtml);
    });
    
    updateCartSummary();
}

function loadSampleCart() {
    currentCart = {
        items: [
            { product_id: 1, quantity: 6, price: 50 },
            { product_id: 2, quantity: 3, price: 30 },
            { product_id: 3, quantity: 2, price: 25 }
        ]
    };
    updateCartDisplay();
    showToast('Sample cart loaded!', 'success');
}

function updateCartSummary() {
    const validItems = currentCart.items.filter(item => 
        item.product_id && item.quantity && item.price
    );
    
    const total = validItems.reduce((sum, item) => 
        sum + (item.quantity * item.price), 0
    );
    
    const summaryHtml = `
        <div class="row">
            <div class="col-md-6">
                <h6>Cart Items: ${validItems.length}</h6>
                <h6>Total Quantity: ${validItems.reduce((sum, item) => sum + item.quantity, 0)}</h6>
            </div>
            <div class="col-md-6">
                <h5 class="text-end">Total: $${total.toFixed(2)}</h5>
            </div>
        </div>
    `;
    
    document.getElementById('cart-summary').innerHTML = summaryHtml;
}

async function findApplicableCoupons() {
    const validItems = currentCart.items.filter(item => 
        item.product_id && item.quantity && item.price
    );
    
    if (validItems.length === 0) {
        showToast('Please add valid items to the cart first', 'warning');
        return;
    }
    
    const applicableCouponsContainer = document.getElementById('applicable-coupons');
    applicableCouponsContainer.innerHTML = `
        <div class="text-center">
            <div class="spinner-border spinner-border-sm" role="status"></div>
            <p class="mt-2">Finding applicable coupons...</p>
        </div>
    `;
    
    try {
        const response = await fetch(`${API_BASE_URL}/applicable-coupons`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ cart: { items: validItems } })
        });
        
        const data = await response.json();
        const applicableCoupons = data.applicable_coupons || [];
        
        if (applicableCoupons.length === 0) {
            applicableCouponsContainer.innerHTML = `
                <div class="alert alert-info">
                    <i class="fas fa-info-circle me-2"></i>
                    No applicable coupons found for this cart.
                </div>
            `;
            return;
        }
        
        let couponsHtml = '<div class="list-group">';
        applicableCoupons.forEach(coupon => {
            couponsHtml += `
                <div class="list-group-item coupon-card">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <h6 class="mb-1">
                                <span class="coupon-type-badge bg-primary text-white">${coupon.type}</span>
                                Coupon #${coupon.coupon_id}
                            </h6>
                            <p class="mb-1">Discount: $${coupon.discount.toFixed(2)}</p>
                        </div>
                        <button class="btn btn-success btn-sm" onclick="applyCoupon(${coupon.coupon_id})">
                            <i class="fas fa-check me-2"></i>Apply
                        </button>
                    </div>
                </div>
            `;
        });
        couponsHtml += '</div>';
        
        applicableCouponsContainer.innerHTML = couponsHtml;
        showToast(`Found ${applicableCoupons.length} applicable coupon(s)!`, 'success');
        
    } catch (error) {
        applicableCouponsContainer.innerHTML = `
            <div class="alert alert-danger">
                <i class="fas fa-exclamation-triangle me-2"></i>
                Error finding applicable coupons. Please try again.
            </div>
        `;
        console.error('Error finding applicable coupons:', error);
    }
}

async function applyCoupon(couponId) {
    const validItems = currentCart.items.filter(item => 
        item.product_id && item.quantity && item.price
    );
    
    try {
        const response = await fetch(`${API_BASE_URL}/apply-coupon/${couponId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ cart: { items: validItems } })
        });
        
        const data = await response.json();
        
        if (response.ok) {
            // Update cart summary with discount information
            const summaryHtml = `
                <div class="row">
                    <div class="col-md-6">
                        <h6>Cart Items: ${data.updated_cart.items.length}</h6>
                        <h6>Total Quantity: ${data.updated_cart.items.reduce((sum, item) => sum + item.quantity, 0)}</h6>
                    </div>
                    <div class="col-md-6">
                        <div class="text-end">
                            <p class="mb-1">Original Total: $${data.total_price.toFixed(2)}</p>
                            <p class="mb-1 text-success">Discount: -$${data.total_discount.toFixed(2)}</p>
                            <h5 class="text-primary">Final Total: $${data.final_price.toFixed(2)}</h5>
                        </div>
                    </div>
                </div>
                <div class="alert alert-success mt-3">
                    <i class="fas fa-check-circle me-2"></i>
                    Coupon applied successfully! You saved $${data.total_discount.toFixed(2)}
                </div>
            `;
            
            document.getElementById('cart-summary').innerHTML = summaryHtml;
            showToast(`Coupon applied! You saved $${data.total_discount.toFixed(2)}`, 'success');
        } else {
            showToast(`Error applying coupon: ${data.message}`, 'error');
        }
    } catch (error) {
        showToast('Network error. Please try again.', 'error');
        console.error('Error applying coupon:', error);
    }
}

// Utility Functions
function showToast(message, type = 'info') {
    const toast = document.getElementById('toast');
    const toastBody = document.getElementById('toast-body');
    
    // Set toast content
    toastBody.textContent = message;
    
    // Set toast type
    toast.className = `toast toast-${type}`;
    
    // Show toast
    const bsToast = new bootstrap.Toast(toast);
    bsToast.show();
}
