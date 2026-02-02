
\restrict h0iTsQhdwqP8RvQDIH4TnaOUTplYOFpQVg8arBvFzaX9DXYFzYYwhkqSdlMum8c


SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;


CREATE TABLE public.campaign (
    campaign_id integer NOT NULL,
    charity_id integer,
    title character varying(100) NOT NULL,
    goal_amount numeric(12,2) NOT NULL,
    status character varying(30) NOT NULL,
    current_amount numeric(12,2) DEFAULT 0
);


ALTER TABLE public.campaign OWNER TO postgres;


CREATE TABLE public.charity (
    charity_id integer NOT NULL,
    charity_name character varying(100) NOT NULL,
    reg_number character varying(50) NOT NULL
);


ALTER TABLE public.charity OWNER TO postgres;


CREATE TABLE public.donation (
    donation_id integer NOT NULL,
    donor_id integer,
    campaign_id integer,
    amount numeric(10,2) NOT NULL,
    donation_date timestamp without time zone DEFAULT CURRENT_DATE NOT NULL
);


ALTER TABLE public.donation OWNER TO postgres;


ALTER TABLE public.donation ALTER COLUMN donation_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.donation_donation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


CREATE TABLE public.donor (
    donor_id integer NOT NULL,
    full_name character varying(100) NOT NULL,
    email character varying(100) NOT NULL,
    phone character varying(20) NOT NULL,
    address text
);


ALTER TABLE public.donor OWNER TO postgres;


CREATE TABLE public.payment (
    payment_id integer NOT NULL,
    donation_id integer,
    method character varying(50) CONSTRAINT payment_methodd_not_null NOT NULL,
    status character varying(50) NOT NULL,
    payment_date timestamp without time zone DEFAULT CURRENT_DATE NOT NULL
);


ALTER TABLE public.payment OWNER TO postgres;


ALTER TABLE public.payment ALTER COLUMN payment_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.payment_payment_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


ALTER TABLE ONLY public.campaign
    ADD CONSTRAINT campaign_pkey PRIMARY KEY (campaign_id);


ALTER TABLE ONLY public.charity
    ADD CONSTRAINT charity_charity_name_key UNIQUE (charity_name);


ALTER TABLE ONLY public.charity
    ADD CONSTRAINT charity_pkey PRIMARY KEY (charity_id);


ALTER TABLE ONLY public.charity
    ADD CONSTRAINT charity_reg_number_key UNIQUE (reg_number);


ALTER TABLE ONLY public.donation
    ADD CONSTRAINT donation_pkey PRIMARY KEY (donation_id);


ALTER TABLE ONLY public.donor
    ADD CONSTRAINT donor_email_key UNIQUE (email);


ALTER TABLE ONLY public.donor
    ADD CONSTRAINT donor_phone_key UNIQUE (phone);


ALTER TABLE ONLY public.donor
    ADD CONSTRAINT donor_pkey PRIMARY KEY (donor_id);


ALTER TABLE ONLY public.payment
    ADD CONSTRAINT payment_pkey PRIMARY KEY (payment_id);


ALTER TABLE ONLY public.campaign
    ADD CONSTRAINT campaign_charity_id_fkey FOREIGN KEY (charity_id) REFERENCES public.charity(charity_id);


ALTER TABLE ONLY public.donation
    ADD CONSTRAINT donation_campaign_id_fkey FOREIGN KEY (campaign_id) REFERENCES public.campaign(campaign_id);


ALTER TABLE ONLY public.donation
    ADD CONSTRAINT donation_donor_id_fkey FOREIGN KEY (donor_id) REFERENCES public.donor(donor_id);


ALTER TABLE ONLY public.payment
    ADD CONSTRAINT payment_donation_id_fkey FOREIGN KEY (donation_id) REFERENCES public.donation(donation_id);


\unrestrict h0iTsQhdwqP8RvQDIH4TnaOUTplYOFpQVg8arBvFzaX9DXYFzYYwhkqSdlMum8c
