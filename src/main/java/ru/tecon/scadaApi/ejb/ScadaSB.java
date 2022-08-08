package ru.tecon.scadaApi.ejb;

import ru.tecon.scadaApi.entity.FittingsEntity;
import ru.tecon.scadaApi.entity.HistLogEntity;
import ru.tecon.scadaApi.entity.TubesEntity;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Сервис для работы с задвижками и трубами
 * @author Maksim Shchelkonogov
 */
@Stateless
@LocalBean
public class ScadaSB {

    private static final Logger LOGGER = Logger.getLogger(ScadaSB.class.getName());

    @PersistenceContext(unitName = "PostgreDB")
    private EntityManager em;

    public List<TubesEntity> getTubesByBrand(String brand) {
        TypedQuery<TubesEntity> tubeByBrandQuery = em.createNamedQuery("TubesEntity.byBrand", TubesEntity.class);
        tubeByBrandQuery.setParameter(1, brand);
        return tubeByBrandQuery.getResultList();
    }

    public List<FittingsEntity> getFittingsByBrand(String brand) {
        TypedQuery<FittingsEntity> fittingByBrandQuery = em.createNamedQuery("FittingEntity.byBrand", FittingsEntity.class);
        fittingByBrandQuery.setParameter(1, brand);
        return fittingByBrandQuery.getResultList();
    }

    public TubesEntity getTubeByMuid(String muid) {
        return em.find(TubesEntity.class, muid);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public boolean addTube(TubesEntity tube) {
        try {
            em.persist(tube);
        } catch (PersistenceException ex) {
            LOGGER.log(Level.WARNING, "error create tube", ex);
            return false;
        }
        return true;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public boolean updateTube(String muid, TubesEntity newTube) {
        TubesEntity tube = getTubeByMuid(muid);

        if (tube == null) {
            LOGGER.log(Level.WARNING, "no tube fined {0}", muid);
            return false;
        }

        if (newTube.getClientId() != null) {
            tube.setClientId(newTube.getClientId());
        }
        if (newTube.getStatus() != null) {
            tube.setStatus(newTube.getStatus());
        }

        try {
            em.merge(tube);
        } catch (PersistenceException ex) {
            LOGGER.log(Level.WARNING, "error update tube", ex);
            return false;
        }
        return true;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public boolean deleteTube(String muid) {
        TubesEntity tube = getTubeByMuid(muid);

        if (tube == null) {
            LOGGER.log(Level.WARNING, "no tube fined {0}", muid);
            return false;
        }

        try {
            em.remove(tube);
        } catch (PersistenceException ex) {
            LOGGER.log(Level.WARNING, "error remove tube", ex);
            return false;
        }
        return true;
    }

    public FittingsEntity getFittingByMuid(String muid) {
        return em.find(FittingsEntity.class, muid);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public boolean addFitting(FittingsEntity fitting) {
        try {
            em.persist(fitting);
        } catch (PersistenceException ex) {
            LOGGER.log(Level.WARNING, "error create fitting", ex);
            return false;
        }
        return true;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public boolean updateFitting(String muid, FittingsEntity newFitting) {
        FittingsEntity fitting = getFittingByMuid(muid);

        if (fitting == null) {
            LOGGER.log(Level.WARNING, "no fitting find {0}", muid);
            return false;
        }

        if (newFitting.getFitName() != null) {
            fitting.setFitName(newFitting.getFitName());
        }
        if (newFitting.getFitNum() != null) {
            fitting.setFitNum(newFitting.getFitNum());
        }
        if (newFitting.getFitType() != null) {
            fitting.setFitType(newFitting.getFitType());
        }
        if (newFitting.getFitDesc() != null) {
            fitting.setFitDesc(newFitting.getFitDesc());
        }
        if (newFitting.getFitDu() != null) {
            fitting.setFitDu(newFitting.getFitDu());
        }
        if (newFitting.getFitDriveType() != null) {
            fitting.setFitDriveType(newFitting.getFitDriveType());
        }
        if (newFitting.getFitPower() != null) {
            fitting.setFitPower(newFitting.getFitPower());
        }
        if (newFitting.getFitStat() != null) {
            fitting.setFitStat(newFitting.getFitStat());
        }
        if (newFitting.getFitBypassStat() != null) {
            fitting.setFitBypassStat(newFitting.getFitBypassStat());
        }
        if (newFitting.getFitJumperStat() != null) {
            fitting.setFitJumperStat(newFitting.getFitJumperStat());
        }
        if (newFitting.getClientId() != null) {
            fitting.setClientId(newFitting.getClientId());
        }

        try {
            em.merge(fitting);
        } catch (PersistenceException ex) {
            LOGGER.log(Level.WARNING, "error update fitting", ex);
            return false;
        }
        return true;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public boolean deleteFitting(String muid) {
        FittingsEntity fitting = getFittingByMuid(muid);

        if (fitting == null) {
            LOGGER.log(Level.WARNING, "no fitting find {0}", muid);
            return false;
        }

        try {
            em.remove(fitting);
        } catch (PersistenceException ex) {
            LOGGER.log(Level.WARNING, "error remove fitting", ex);
            return false;
        }
        return true;
    }

    public List<HistLogEntity> getHistByMuid(String muid) {
        TypedQuery<HistLogEntity> query = em.createNamedQuery("HistLogEnity.byMuid", HistLogEntity.class);
        query.setParameter(1, muid);
        return query.getResultList();
    }

    public List<HistLogEntity> getHistByMuidAndDate(String muid, LocalDateTime startDate, LocalDateTime endDate) {
        TypedQuery<HistLogEntity> query = em.createNamedQuery("HistLogEnity.byMuidAndDate", HistLogEntity.class);
        query.setParameter(1, muid);
        query.setParameter(2, startDate);
        query.setParameter(3, endDate);
        return query.getResultList();
    }

    public List<HistLogEntity> getHistByDate(LocalDateTime startDate, LocalDateTime endDate) {
        TypedQuery<HistLogEntity> query = em.createNamedQuery("HistLogEnity.byDate", HistLogEntity.class);
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);
        return query.getResultList();
    }
}
