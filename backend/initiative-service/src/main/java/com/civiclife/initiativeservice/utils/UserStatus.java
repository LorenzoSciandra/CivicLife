package com.civiclife.initiativeservice.utils;

public enum UserStatus {
    ACTIVE, // può fare tutto quello che vuole per le iniziative
    SUSPENDED, // può solo vedere le iniziative e partecipare
    BANNED // non può fare niente, non può creare le iniziative
}