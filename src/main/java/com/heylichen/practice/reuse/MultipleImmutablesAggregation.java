package com.heylichen.practice.reuse;

import java.util.List;

/**
 * Created by lc on 2016/9/3.
 */
public class MultipleImmutablesAggregation {
  private static class ProductByVendor {
    private String vendor;
    private Float price;
    private Float cost;

    public String getVendor() {
      return vendor;
    }

    public void setVendor(String vendor) {
      this.vendor = vendor;
    }

    public Float getPrice() {
      return price;
    }

    public void setPrice(Float price) {
      this.price = price;
    }

    public Float getCost() {
      return cost;
    }

    public void setCost(Float cost) {
      this.cost = cost;
    }

    private static class CollectedPrice {
      private Float totalPrice;
      private Float totalCost;

      public Float getTotalPrice() {
        return totalPrice;
      }

      public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
      }

      public Float getTotalCost() {
        return totalCost;
      }

      public void setTotalCost(Float totalCost) {
        this.totalCost = totalCost;
      }
    }

    public static CollectedPrice calculatePrice(List<ProductByVendor> productByVendorList, List<ProductByVendor> productByVendorListB) {
      Float totalPrice = 0F;
      Float totalCost = 0F;
      for (ProductByVendor productByVendor : productByVendorList) {
        //this logic can be complex due to business requirement
        if (productByVendor != null && productByVendor.getPrice() != null) {
          totalPrice += productByVendor.getPrice();
        }
        if (productByVendor != null && productByVendor.getCost() != null) {
          totalCost += productByVendor.getCost();
        }
      }
      for (ProductByVendor productByVendor : productByVendorListB) {
        if (productByVendor != null && productByVendor.getPrice() != null) {
          totalPrice += productByVendor.getPrice();
        }
        if (productByVendor != null && productByVendor.getCost() != null) {
          totalCost += productByVendor.getCost();
        }
      }
      CollectedPrice result = new CollectedPrice();
      result.setTotalPrice(totalPrice);
      result.setTotalCost(totalCost);
      return result;
    }

    /**
     * code reuse version
     * @param productByVendorList
     * @param productByVendorListB
     * @return
     */
    public static CollectedPrice calculatePriceOptimized(List<ProductByVendor> productByVendorList, List<ProductByVendor> productByVendorListB) {
      CollectedPrice result = new CollectedPrice();
      result.setTotalPrice(0f);
      result.setTotalCost(0f);
      for (ProductByVendor productByVendor : productByVendorList) {
        //this logic can be complex due to business requirement
        doCalculate(result, productByVendor.getPrice(), productByVendor.getCost());
      }
      for (ProductByVendor productByVendor : productByVendorListB) {
        doCalculate(result, productByVendor.getPrice(), productByVendor.getCost());
      }
      return result;
    }

    /**
     * this will create a new CollectedPrice in each itration
     * @param totalPrice
     * @param totalCost
     * @param priceByVendor
     * @param costByVendor
     * @return
     */
    private static CollectedPrice doCalculate(Float totalPrice, Float totalCost, Float priceByVendor, Float costByVendor) {
      Float resultPrice = totalPrice;
      Float resultCost = totalCost;

      if (priceByVendor != null) {
        resultPrice += priceByVendor;
      }
      if (costByVendor != null) {
        resultCost += costByVendor;
      }
      CollectedPrice result = new CollectedPrice();
      result.setTotalPrice(resultPrice);
      result.setTotalCost(resultCost);
      return result;
    }

    /**
     * avoiding unnecessary object creation
     * @param total
     * @param priceByVendor
     * @param costByVendor
     */
    private static void doCalculate(CollectedPrice total, Float priceByVendor, Float costByVendor) {
      if (priceByVendor != null) {
        total.setTotalPrice(total.getTotalPrice()+priceByVendor);
      }
      if (costByVendor != null) {
        total.setTotalCost(total.getTotalCost() + costByVendor);
      }
    }

  }


}
