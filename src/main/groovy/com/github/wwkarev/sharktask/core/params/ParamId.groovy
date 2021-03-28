package com.github.wwkarev.sharktask.core.params;

class ParamId {
    private static final Long CREATOR = 1
    private static final Long ASSIGNEE = 2
    private static final Long STATUS = 3
    private static final Long SUMMARY = 4
    private static final Long FIELD_TYPE = 5

    static Long getCREATOR() {
        return CREATOR
    }

    static Long getASSIGNEE() {
        return ASSIGNEE
    }

    static Long getSTATUS() {
        return STATUS
    }

    static Long getSUMMARY() {
        return SUMMARY
    }

    static Long getFIELD_TYPE() {
        return FIELD_TYPE
    }
}
