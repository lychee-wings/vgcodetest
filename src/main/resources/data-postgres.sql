

-- Some data for quick test.
--INSERT INTO public.ingestion_history ("source") VALUES('initialization');
--
--INSERT INTO public.game_sales (cost_price,game_no,sale_price,tax,"type",game_code,date_of_sale,game_name, ingestion_id) VALUES
--	 (59.0,90,64.31,0.09,2,'PS3','2024-04-01 10:00:00','Assassin''s Creed III', (SELECT id from public.ingestion_history where "source"='initialization')),
--	 (70.0,80,76.3,0.09,2,'PS4','2024-04-02 11:00:00','Fallout 4', (SELECT id from public.ingestion_history where "source"='initialization'));


SELECT * FROM public.game_sales;