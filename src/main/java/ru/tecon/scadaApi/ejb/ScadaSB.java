package ru.tecon.scadaApi.ejb;

import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.postgresql.util.PSQLException;
import ru.tecon.scadaApi.ScadaApiException;
import ru.tecon.scadaApi.entity.FittingsEntity;
import ru.tecon.scadaApi.entity.HistLogEntity;
import ru.tecon.scadaApi.entity.PassportEntity;
import ru.tecon.scadaApi.entity.TubesEntity;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
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
 *
 * @author Maksim Shchelkonogov
 */
@Stateless
@LocalBean
public class ScadaSB {

    @Inject
    private Logger logger;

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
    public void addTube(TubesEntity tube) throws ScadaApiException {
        try {
            em.persist(tube);
            em.flush();
        } catch (PersistenceException ex) {
            logger.log(Level.WARNING, "error create tube", ex);
            parsePersistenceError(ex);
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void updateTube(String muid, TubesEntity newTube) throws ScadaApiException {
        TubesEntity tube = getTubeByMuid(muid);

        if (tube == null) {
            logger.log(Level.WARNING, "no tube fined {0}", muid);
            throw new ScadaApiException("no entity for muid " + muid);
        }

        if (newTube.getClientId() != null) {
            tube.setClientId(newTube.getClientId());
        }
        if (newTube.getStatus() != null) {
            tube.setStatus(newTube.getStatus());
        }

        try {
            em.merge(tube);
            em.flush();
        } catch (PersistenceException ex) {
            logger.log(Level.WARNING, "error update tube", ex);
            throw new ScadaApiException();
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public boolean deleteTube(String muid) {
        TubesEntity tube = getTubeByMuid(muid);

        if (tube == null) {
            logger.log(Level.WARNING, "no tube fined {0}", muid);
            return false;
        }

        try {
            em.remove(tube);
        } catch (PersistenceException ex) {
            logger.log(Level.WARNING, "error remove tube", ex);
            return false;
        }
        return true;
    }

    public FittingsEntity getFittingByMuid(String muid) {
        return em.find(FittingsEntity.class, muid);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void addFitting(FittingsEntity fitting) throws ScadaApiException {
        try {
            em.persist(fitting);
            em.flush();
        } catch (PersistenceException ex) {
            logger.log(Level.WARNING, "error create fitting", ex);
            parsePersistenceError(ex);
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void updateFitting(String muid, FittingsEntity newFitting) throws ScadaApiException {
        FittingsEntity fitting = getFittingByMuid(muid);

        if (fitting == null) {
            logger.log(Level.WARNING, "no fitting find {0}", muid);
            throw new ScadaApiException("no entity for muid " + muid);
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
            em.flush();
        } catch (PersistenceException ex) {
            logger.log(Level.WARNING, "error update fitting", ex);
            throw new ScadaApiException();
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public boolean deleteFitting(String muid) {
        FittingsEntity fitting = getFittingByMuid(muid);

        if (fitting == null) {
            logger.log(Level.WARNING, "no fitting find {0}", muid);
            return false;
        }

        try {
            em.remove(fitting);
        } catch (PersistenceException ex) {
            logger.log(Level.WARNING, "error remove fitting", ex);
            return false;
        }
        return true;
    }

    public List<HistLogEntity> getHistByMuid(String muid) {
        TypedQuery<HistLogEntity> query = em.createNamedQuery("HistLogEntity.byMuid", HistLogEntity.class);
        query.setParameter(1, muid);
        return query.getResultList();
    }

    public List<HistLogEntity> getHistByMuidAndDate(String muid, LocalDateTime startDate, LocalDateTime endDate) {
        TypedQuery<HistLogEntity> query = em.createNamedQuery("HistLogEntity.byMuidAndDate", HistLogEntity.class);
        query.setParameter(1, muid);
        query.setParameter(2, startDate);
        query.setParameter(3, endDate);
        return query.getResultList();
    }

    public List<HistLogEntity> getHistByDate(LocalDateTime startDate, LocalDateTime endDate) {
        TypedQuery<HistLogEntity> query = em.createNamedQuery("HistLogEntity.byDate", HistLogEntity.class);
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);
        return query.getResultList();
    }

    private void parsePersistenceError(PersistenceException ex) throws ScadaApiException {
        PSQLException psqlEx = null;

        if (ex.getCause() instanceof ConstraintViolationException) {
            ConstraintViolationException constraintEx = (ConstraintViolationException) ex.getCause();
            if (constraintEx.getCause() instanceof PSQLException) {
                psqlEx = (PSQLException) constraintEx.getCause();
            }
        }

        if (ex.getCause() instanceof DataException) {
            DataException dataEx = (DataException) ex.getCause();
            if (dataEx.getCause() instanceof PSQLException) {
                psqlEx = (PSQLException) dataEx.getCause();
            }
        }

        if ((psqlEx != null) && (psqlEx.getServerErrorMessage() != null) && (psqlEx.getServerErrorMessage().getSQLState() != null))  {
            switch (psqlEx.getServerErrorMessage().getSQLState()) {
                case "23502":
                    throw new ScadaApiException("Column " + psqlEx.getServerErrorMessage().getColumn() + " must be not null");
                case "23505":
                    throw new ScadaApiException("Column not unique. " + psqlEx.getServerErrorMessage().getDetail());
                case "22001":
                    throw new ScadaApiException("Value too long for type");
            }
        }

        throw new ScadaApiException("Server error");
    }

    public PassportEntity getPassportByMuid(long muid) {
        return em.find(PassportEntity.class, muid);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public boolean deletePassport(long muid) {
        PassportEntity passport = getPassportByMuid(muid);

        if (passport == null) {
            logger.log(Level.WARNING, "no passport find {0}", muid);
            return false;
        }

        try {
            em.remove(passport);
        } catch (PersistenceException ex) {
            logger.log(Level.WARNING, "error remove passport " + passport, ex);
            return false;
        }
        return true;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void addPassport(PassportEntity passport) throws ScadaApiException {
        try {
            em.persist(passport);
            em.flush();
        } catch (PersistenceException ex) {
            logger.log(Level.WARNING, "error create passport", ex);
            parsePersistenceError(ex);
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void updatePassport(long muid, PassportEntity newPassport) throws ScadaApiException {
        PassportEntity passport = getPassportByMuid(muid);

        if (passport == null) {
            logger.log(Level.WARNING, "no passport find {0}", muid);
            throw new ScadaApiException("no passport for muid " + muid);
        }

        if (newPassport.getAddress() != null) {
            passport.setAddress(newPassport.getAddress());
        }
        if (newPassport.getFirstName() != null) {
            passport.setFirstName(newPassport.getFirstName());
        }
        if (newPassport.getSecondName() != null) {
            passport.setSecondName(newPassport.getSecondName());
        }
        passport.setStatus(newPassport.getStatus());

        try {
            em.merge(passport);
            em.flush();
        } catch (PersistenceException ex) {
            logger.log(Level.WARNING, "error update passport", ex);
            throw new ScadaApiException();
        }
    }
}
