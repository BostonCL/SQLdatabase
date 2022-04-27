CREATE FUNCTION Incident_label(IN b CHAR(20))
	RETURNS  CHAR(20)
    DECLARE num INT
BEGIN
	SET num = (SELECT COUNT(*)
			  INTO quantity
              FROM INCIDENT
              WHERE MONTH(date_added) = monthAtr)
    IF num <= 2 THEN RETURN 'low'
    ELSEIF num > 2 OR num <= 5 THEN RETURN
		'moderate'
	ELSE RETURN 'high'
    END IF
END;
