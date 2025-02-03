package ku.cs.restaurant.service;

import ku.cs.restaurant.entity.Order;
import ku.cs.restaurant.entity.Review;
import ku.cs.restaurant.repository.OrderRepository;
import ku.cs.restaurant.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;

    public Review submitReview(UUID orderId, UUID customerId, int rating, String comment) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getUser().getId().equals(customerId) || !order.getStatus().equals("DELIVERED")) {
            throw new RuntimeException("Review can only be submitted for completed orders.");
        }

        Review review = new Review();
        review.setOrder(order);
        review.setCustomer(order.getUser());
        review.setRating(rating);
        review.setComment(comment);
        return reviewRepository.save(review);
    }
}