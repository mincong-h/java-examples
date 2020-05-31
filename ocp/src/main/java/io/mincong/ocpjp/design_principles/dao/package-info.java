/**
 * The DAO pattern abstracts and encapsulates all access to a data source (flat files, relational
 * databases, XML, JSON, or any other data source). It manages the connection with the data source
 * to access and store the data. It shields a client from knowing how to retrieve or store data and
 * lets it specify what data to retrieve and store. So it makes the client code flexible to work
 * with multiple data sources.
 *
 * <p>The DAO pattern decouples classes that define business or presentation logic from the data
 * persistence details. The CRUD operations (Create, Read, Update, Delete) form the basis of the DAO
 * pattern.
 *
 * @author Mincong Huang
 */
package io.mincong.ocpjp.design_principles.dao;
