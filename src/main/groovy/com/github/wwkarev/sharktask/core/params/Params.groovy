package com.github.wwkarev.sharktask.core.params

import com.github.wwkarev.sharktask.api.params.ParamNotFoundException
import com.github.wwkarev.sharktask.api.params.Params as API_Params

class Params implements API_Params {
    private Map<Long, Object> storage = [:]

    @Override
    void add(Long id, Object value) {
        storage[id] = value

    }

    @Override
    Object get(Long id) {
        Object value = storage[id]
        if (value == null) {
            throw new ParamNotFoundException()
        }
        return value
    }

    @Override
    Object getAt(Long id) {
        return storage[id]
    }

    @Override
    List<Long> getIdList() {
        return new ArrayList(storage.keySet())
    }
}
