package org.airavata.teamzenith.init.repository;


import org.airavata.teamzenith.init.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
