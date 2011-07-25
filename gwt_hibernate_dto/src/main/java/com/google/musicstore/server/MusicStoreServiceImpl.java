package com.google.musicstore.server;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.musicstore.client.MusicStoreService;
import com.google.musicstore.client.dto.AccountDTO;
import com.google.musicstore.client.dto.RecordDTO;
import com.google.musicstore.domain.Account;
import com.google.musicstore.domain.Record;
import com.google.musicstore.util.HibernateUtil;

public class MusicStoreServiceImpl extends RemoteServiceServlet implements
    MusicStoreService {

  public List<AccountDTO> getAccounts() {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();
    List<Account> accounts = new ArrayList<Account>(session.createQuery(
        "from Account").list());
    List<AccountDTO> accountDTOs = new ArrayList<AccountDTO>(
        accounts != null ? accounts.size() : 0);
    if (accounts != null) {
      for (Account account : accounts) {
        accountDTOs.add(createAccountDTO(account));
      }
    }
    session.getTransaction().commit();
    return accountDTOs;
  }

  public List<RecordDTO> getRecords() {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();
    List<Record> records = new ArrayList<Record>(session.createQuery(
        "from Record").list());
    List<RecordDTO> recordDTOs = new ArrayList<RecordDTO>(
        records != null ? records.size() : 0);
    if (records != null) {
      for (Record record : records) {
        recordDTOs.add(createRecordDTO(record));
      }
    }
    session.getTransaction().commit();
    return recordDTOs;
  }

  public Long saveAccount(AccountDTO accountDTO) {
    Account account = new Account(accountDTO);
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();
    session.save(account);
    session.getTransaction().commit();
    return account.getId();
  }

  public Long saveRecord(RecordDTO recordDTO) {
    Record record = new Record(recordDTO);
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();
    session.save(record);
    session.getTransaction().commit();
    return record.getId();
  }

  public void saveRecordToAccount(AccountDTO accountDTO, RecordDTO recordDTO) {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();
    Account account = (Account) session.load(Account.class, accountDTO.getId());
    Record record = (Record) session.load(Record.class, recordDTO.getId());
    account.addRecord(record);
    session.save(account);
    session.getTransaction().commit();
  }

  public List<AccountDTO> getAllAccountRecords() {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();
    List<Account> accounts = new ArrayList<Account>(session.createQuery(
        "from Account").list());
    List<AccountDTO> accountDTOs = new ArrayList<AccountDTO>(
        accounts != null ? accounts.size() : 0);
    if (accounts != null) {
      for (Account account : accounts) {
        accountDTOs.add(createAccountDTO(account));
      }
    }
    session.getTransaction().commit();
    return accountDTOs;
  }

  private AccountDTO createAccountDTO(Account account) {
    Set<Record> records = account.getRecords();
    Set<RecordDTO> recordDTOs = new HashSet<RecordDTO>(
        records != null ? records.size() : 0);
    if (records != null) {
      for (Record record : records) {
        recordDTOs.add(createRecordDTO(record));
      }
    }
    return new AccountDTO(account.getId(), account.getName(), account
        .getPassword(), recordDTOs);
  }

  private RecordDTO createRecordDTO(Record record) {
    return new RecordDTO(record.getId(), record.getTitle(), record.getYear(),
        record.getPrice());
  }
}