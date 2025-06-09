package kz.asemokamichi.maliknet.service;

import kz.asemokamichi.maliknet.data.entity.Bid;
import org.springframework.stereotype.Service;

@Service
public interface BidService {
    Bid createBid(Bid bid);
}
