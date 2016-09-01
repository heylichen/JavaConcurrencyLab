package com.heylichen.guava.cache;

import com.google.common.cache.CacheLoader;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lc on 2016/8/13.
 */
public class PersonCacheLoader extends CacheLoader<Integer, Person> {
  @Override
  public Person load(Integer id) throws Exception {
    Thread.sleep(200);
    return loadOne(id);
  }

  @Override
  public Map<Integer, Person> loadAll(Iterable<? extends Integer> keys) throws Exception {
    Thread.sleep(200);
    if (keys == null || !keys.iterator().hasNext()) {
      return Collections.emptyMap();
    }
    Map<Integer, Person> personMap = new HashMap<>();
    keys.forEach(id -> {
          Person person = loadOne(id);
          if (person != null) {
            personMap.put(person.getId(), person);
          }
        }
    );
    return personMap;
  }

  private Person loadOne(Integer id) {

    Person person = new Person();
    person.setId(id);
    person.setName("person " + id);
    return person;

  }
}
