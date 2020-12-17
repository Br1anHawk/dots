package spring.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByNickname(String nickname);
}
