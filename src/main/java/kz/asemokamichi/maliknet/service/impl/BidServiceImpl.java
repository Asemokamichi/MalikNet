package kz.asemokamichi.maliknet.service.impl;

import kz.asemokamichi.maliknet.data.entity.Bid;
import kz.asemokamichi.maliknet.repository.BidRepository;
import kz.asemokamichi.maliknet.service.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BidServiceImpl implements BidService {
    private final BidRepository bidRepository;

    @Override
    public Bid createBid(Bid bid) {
        return bidRepository.save(bid);
    }

}
