package com.food.ordering.system.restaurant.service.domain.ports.output.message.publisher;

import com.food.ordering.system.restaurant.service.domain.dto.RestaurantApprovalRequest;

public interface RestaurantApprovalResponseMessagePublisher {

    void approveOrder(RestaurantApprovalRequest restaurantApprovalRequest);
}
