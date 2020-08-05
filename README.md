# Swiggy client app clone

## Please refer the screenrecord.mp4 for the working of the app.

Its an complete Swiggy app clone with features like
* Onboarding screen with feature list
* OTP verification and auto verification are added
* If the user exist already he is redirected to the home scree, if not he is redirected to the registration screen
* List of all Shops prioritized based on its rating
* An global search, which gives the result of the dishes and shop in that name. i.e. both shop and dishes details can be searched simultaneously
* Shop activity lists the dishes available in the shop
* The shop can be rated in this activity
* The shop image, timings, delivery options, delivery price etc. are displayed
* The dishes have these toggle button as that of swiggy, which shows "add" when not added to the cart then changes to "-1+", where we can use "-" or "+" to add or delete quantity of dishes.
* Every shop has their available option. If the option is false, the swiggy app disables the shop with grey tint.
* An account page, which has the following features
  * Shallow details of ongoing, cancelled, delivered, transaction failed etc of the orders
  * The following features are available in the detailed orders page when order details are clicked
    * Realtime status update with time and status details
    * OTP for delivery verification
    * Order summary details
    * Cancel option for orders
    * Delivery status
    * An option for re-ordering the past order, easy to order frequent dishes
   * The single list view shows last 10 orders, when "view more" is clicked another 10 is listed and the option disappears when there are no more orders to display
   * Account details update option which can be used to change account details
* Cart activity has the detailed overview of the current ongoing order. The cart updates itself whenever an dish is ordered from the scope of the app i.e. shop, search, prev order.

The backend used here is the [zinger framework](https://zinger.pw/#/) which was developed on spring framework.
