SELECT *
FROM npcs
WHERE lower(name) LIKE ?
ORDER BY name;