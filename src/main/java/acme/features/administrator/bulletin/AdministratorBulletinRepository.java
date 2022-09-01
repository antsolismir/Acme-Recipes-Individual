package acme.features.administrator.bulletin;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;

import acme.entities.bulletin.Bulletin;
import acme.entities.systemConfiguration.SystemConfiguration;
import acme.framework.repositories.AbstractRepository;

public interface AdministratorBulletinRepository extends AbstractRepository{

	@Query("select b from Bulletin b where b.id = :id")
	Bulletin findOneBulletinById(int id);

	@Query("select b from Bulletin b")
	Collection<Bulletin> findAllBulletins();

	@Query("select b from Bulletin b where b.instantiationMoment > :deadline")
	Collection<Bulletin> findRecentBulletins(Date deadline);
	
	@Query("select c from SystemConfiguration c")
	SystemConfiguration getSystemConfiguration();

}