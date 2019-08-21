# Live Order Board application

* It's a very lightweight application built without using much external api's in the code.
* Entrypoint LiveOrderBoardFactory class
* You can use it as a dependent jar in any application, just add this as a dependency jar to your application.
* I have added SpringBoot support so that it can be autowired (LiveOrderService)as a bean to any application not tested the autowiring stuff.
* LiveOrderBoardFactory gives you an instance of LiveOrderService.class and creates instances of all the dependent classes.
* It's not using Spring injection as I wasn't sure whether I should be using any third party jars.
* It uses Google guava jar just to create a ImmutableMap of LiveOrderSummary.
* The service returns ImmutableMap<String, List<OrderSummary>> which can be very well formatted to whatever way we want to display.
* I have added a very quick Integration test just to see it's working properly and it demonstrates the exact display as per the requirements.
