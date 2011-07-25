package com.google.musicstore.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.musicstore.client.dto.AccountDTO;
import com.google.musicstore.client.dto.RecordDTO;

public interface MusicStoreServiceAsync {
  public void getAccounts(AsyncCallback<List<AccountDTO>> callback);

  public void getRecords(AsyncCallback<List<RecordDTO>> callback);

  public void saveAccount(AccountDTO accountDTO, AsyncCallback<Long> callback);

  public void saveRecord(RecordDTO record, AsyncCallback<Long> callback);

  public void saveRecordToAccount(AccountDTO accountDTO, RecordDTO recordDTO,
                                  AsyncCallback<Void> callback);

  public void getAllAccountRecords(AsyncCallback<List<AccountDTO>> callback);
}