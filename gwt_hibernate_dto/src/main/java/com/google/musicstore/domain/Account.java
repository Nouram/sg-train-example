package com.google.musicstore.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.google.musicstore.client.dto.AccountDTO;
import com.google.musicstore.client.dto.RecordDTO;

public class Account implements Serializable {
  Long id;
  String name;
  String password;
  Set<Record> records;

  public Account() {
  }

  public Account(Long id) {
    this.id = id;
  }

  public Account(AccountDTO accountDTO) {
    id = accountDTO.getId();
    name = accountDTO.getName();
    password = accountDTO.getPassword();
    Set<RecordDTO> recordDTOs = accountDTO.getRecords();
    if (recordDTOs != null) {
      Set<Record> records = new HashSet<Record>(recordDTOs.size());
      for (RecordDTO recordDTO : recordDTOs) {
        records.add(new Record(recordDTO));
      }
      this.records = records;
    }
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Record> getRecords() {
    return records;
  }

  public void setRecords(Set<Record> records) {
    this.records = records;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void addRecord(Record record) {
    if (records == null) {
      records = new HashSet<Record>();
    }
    records.add(record);
  }

  public void removeRecord(Record record) {
    if (records == null) {
      return;
    }
    records.remove(record);
  }
}
