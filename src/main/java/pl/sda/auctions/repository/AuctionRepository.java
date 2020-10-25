package pl.sda.auctions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.sda.auctions.model.Auction;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {

}
