package acme.features.authenticated.bulletin;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.bulletin.Bulletin;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedBulletinRepository extends AbstractRepository{
	
	@Query("select a from Bulletin a where a.instantiationMoment > :deadline")
	Collection<Bulletin> findBulletinByDate(Date deadline);
	
	@Query("select a from Bulletin a where a.id = :id and a.instantiationMoment > :deadline")
	Bulletin findBulletinById(int id, Date deadline);
}
