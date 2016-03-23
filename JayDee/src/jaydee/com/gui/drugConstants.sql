----------------SELECT d.name,d.code,s.limit,SUM(i.qty)-SUM(p.qty) AS qty FROM pos p, stock s, inventory i, drugs d WHERE d.did = s.did AND p.did=i.did AND i.did=d.did AND p.did=d.did ORDER BY d.name;


----------------SELECT max(pos_id) FROM pos;


-----------SELECT SUM(i.`qty`) FROM inventory i WHERE i.`batch_number`="1";

--------------SELECT SUM(p.`qty`) AS sum_p FROM pos p, inventory i WHERE i.`did` = p.`did`;

------------CREATE VIEW sum_i AS SELECT i.`did`, SUM(i.`qty`) FROM inventory i WHERE i.`batch_number`="1";

-------------------SELECT * FROM sum_i;

----------SELECT * FROM drugs WHERE name LIKE 'h' OR  code LIKE 'h';


-----------------SELECT `code`, `name`, `desc`, price FROM jaydee.drugs ORDER BY `code`,`name`;
SELECT s.name , d.name, d.price , ss.cost FROM supplier s, supplier_stock ss, drugs d WHERE d.did = ss.did AND s.sid = s.sid;
