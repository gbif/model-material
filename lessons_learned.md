# Lessons learned

## Introduction

This document captures observations and challenges encountered during the course of the project to map collection management systems (CMSs) to version 4.5 of the GBIF Unified Model (UM).

## Common Challenges

**The need to create identifiers that don't exist in the original data is common.** This suggests the importance of being able to provide GUIDs on ingest and may also suggest always storing existing local identifiers in the Identifiers table so that the local-UM connection can be tracked by anyone.

**The need to provide "static values" (the same value for a large set of records) that are not in the original data, but that are known, is common.** In publishing paradigm that uses the Integrated Publishing Toolkit, static value mapping is possible in the mapping interfaces.

**The need to concatenate records from multiple source tables into single unified model tables is common.** This presents a challenge for local identifiers that are duplicated between records from two source tables that need to be unified (e.g., `MaterialEntity` and `DigitalEntity` records to the `Entity` table). A solution at the source is required, such as prefixing the identifiers with a string that designates the source table (e.g., for an object and an image each with a local identifier "1", translate these to "object_1", "image_1" in order to populate their supertype `Entity` records without duplication).

## Agents

There are multiple ways in which `Agent` names are represented in CMSs (e.g., "John Q Public", "John Q. Public", "JQ Public", "Public, John Q", "Public, John Q.", "Public, JQ", etc. This makes it difficult to align `Agemts` across data sources. Supporting atomized `Agent` names might be useful.

## DigitalEntities related to Events

Multiple CMSs support the relationship of DigitalEntities to Events. In the target version of the UM (4.5), the direct relationship of DigitalEntities to Events is not supported. Examples include images of field notes and images of landscapes other Event-related phenomena. A broader capacity to relate any two concepts (not just two Entities) in the UM would solve this issue.

## References

The [Literature References Extension](https://rs.gbif.org/extension/gbif/1.0/references.xml) for Darwin Core (not ratified) uses atomized `Reference` properties that might be useful to support. 
