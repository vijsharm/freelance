package com.aa.gsa.service;

import com.aa.gsa.service.impl.CloudantService;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.xml.crypto.Data;

@RunWith(MockitoJUnitRunner.class)
public class CloudantTest {

  @Mock
  private CloudantClient cloudantClient;

  @Mock
  private Database database;

  private CloudantService service;

  @Before
  public void setUp(){
      Mockito.when(cloudantClient.database(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(database);
      service = new CloudantService(cloudantClient, "database");
  }
  @Test
  public void createDatabaseTest() {
      service.createDatabase("databasename", true);
      Mockito.verify(cloudantClient, Mockito.times(1)).database("databasename", true);
  }

}

