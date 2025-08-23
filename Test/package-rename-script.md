# Package Rename Script - From com.zeonvillan.test to com.zeonvillan.couponsystem

## Files to Update:

### 1. Main Application Class
- **Old**: `src/main/java/com/zeonvillan/test/TestApplication.java`
- **New**: `src/main/java/com/zeonvillan/couponsystem/CouponSystemApplication.java`
- **Status**: ✅ Created

### 2. Entity Classes (Update package name in each):
- `src/main/java/com/zeonvillan/test/entity/Coupon.java` → `src/main/java/com/zeonvillan/couponsystem/entity/Coupon.java`
- `src/main/java/com/zeonvillan/test/entity/CartWiseCoupon.java` → `src/main/java/com/zeonvillan/couponsystem/entity/CartWiseCoupon.java`
- `src/main/java/com/zeonvillan/test/entity/ProductWiseCoupon.java` → `src/main/java/com/zeonvillan/couponsystem/entity/ProductWiseCoupon.java`
- `src/main/java/com/zeonvillan/test/entity/BxGyCoupon.java` → `src/main/java/com/zeonvillan/couponsystem/entity/BxGyCoupon.java`
- `src/main/java/com/zeonvillan/test/entity/BuyProduct.java` → `src/main/java/com/zeonvillan/couponsystem/entity/BuyProduct.java`
- `src/main/java/com/zeonvillan/test/entity/GetProduct.java` → `src/main/java/com/zeonvillan/couponsystem/entity/GetProduct.java`

### 3. Repository Classes:
- All files in `src/main/java/com/zeonvillan/test/repository/` → `src/main/java/com/zeonvillan/couponsystem/repository/`

### 4. Service Classes:
- All files in `src/main/java/com/zeonvillan/test/service/` → `src/main/java/com/zeonvillan/couponsystem/service/`

### 5. Controller Classes:
- All files in `src/main/java/com/zeonvillan/test/controller/` → `src/main/java/com/zeonvillan/couponsystem/controller/`

### 6. DTO Classes:
- All files in `src/main/java/com/zeonvillan/test/dto/` → `src/main/java/com/zeonvillan/couponsystem/dto/`

### 7. Model Classes:
- All files in `src/main/java/com/zeonvillan/test/model/` → `src/main/java/com/zeonvillan/couponsystem/model/`

### 8. Exception Classes:
- All files in `src/main/java/com/zeonvillan/test/exception/` → `src/main/java/com/zeonvillan/couponsystem/exception/`

### 9. Config Classes:
- All files in `src/main/java/com/zeonvillan/test/config/` → `src/main/java/com/zeonvillan/couponsystem/config/`

### 10. Test Classes:
- All files in `src/test/java/com/zeonvillan/test/` → `src/test/java/com/zeonvillan/couponsystem/`

### 11. Configuration Files:
- **application.properties**: ✅ Updated logging package
- **application-test.properties**: ✅ Updated logging package
- **pom.xml**: ✅ Updated name, description, and main class

## Steps to Complete:

1. Create new directory structure
2. Copy all files to new locations
3. Update package declarations in all Java files
4. Update import statements in all Java files
5. Update test class package references
6. Delete old directory structure

## Manual Steps Required:

Since this is a large refactoring, you'll need to:

1. **Create the new package structure**:
   ```bash
   mkdir -p src/main/java/com/zeonvillan/couponsystem/{entity,repository,service,controller,dto,model,exception,config}
   mkdir -p src/test/java/com/zeonvillan/couponsystem/{service,controller}
   ```

2. **Copy and update each file**:
   - Copy each Java file to the new location
   - Update the package declaration at the top
   - Update any import statements that reference the old package

3. **Key Import Updates Needed**:
   - Change `import com.zeonvillan.test.*` to `import com.zeonvillan.couponsystem.*`
   - Update cross-package references

4. **Test the changes**:
   - Compile the project: `./mvnw clean compile`
   - Run tests: `./mvnw test`
   - Run the application: `./mvnw spring-boot:run`

## Alternative Approach:

Use IDE refactoring tools:
1. Open project in IntelliJ IDEA or Eclipse
2. Right-click on the `com.zeonvillan.test` package
3. Select "Refactor" → "Rename"
4. Change to `com.zeonvillan.couponsystem`
5. IDE will automatically update all references
