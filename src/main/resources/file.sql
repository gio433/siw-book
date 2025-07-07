--
-- PostgreSQL database dump
--

-- Dumped from database version 17.4
-- Dumped by pg_dump version 17.4

-- Started on 2025-06-08 21:13:46

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

--
-- TOC entry 224 (class 1259 OID 34849)
-- Name: amministratore; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.amministratore (
    id bigint NOT NULL,
    cognome character varying(255),
    nome character varying(255)
);


ALTER TABLE public.amministratore OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 34843)
-- Name: amministratore_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.amministratore_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.amministratore_seq OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 34856)
-- Name: autore; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.autore (
    data_morte date NOT NULL,
    data_nascita date NOT NULL,
    id bigint NOT NULL,
    cognome character varying(255),
    nazionalita character varying(255),
    nome character varying(255)
);


ALTER TABLE public.autore OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 34844)
-- Name: autore_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.autore_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.autore_seq OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 34863)
-- Name: credentials; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.credentials (
    amministratore_id bigint,
    id bigint NOT NULL,
    utente_id bigint,
    password character varying(255) NOT NULL,
    role character varying(255) NOT NULL,
    username character varying(255) NOT NULL
);


ALTER TABLE public.credentials OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 34845)
-- Name: credentials_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.credentials_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.credentials_seq OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 34876)
-- Name: libro; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.libro (
    anno integer NOT NULL,
    id bigint NOT NULL,
    titolo character varying(255)
);


ALTER TABLE public.libro OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 34881)
-- Name: libro_autori; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.libro_autori (
    autori_id bigint NOT NULL,
    libri_id bigint NOT NULL
);


ALTER TABLE public.libro_autori OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 34884)
-- Name: libro_immagini; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.libro_immagini (
    libro_id bigint NOT NULL,
    immagine_url character varying(255)
);


ALTER TABLE public.libro_immagini OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 34775)
-- Name: libro_recensioni; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.libro_recensioni (
    libro_id bigint NOT NULL,
    recensioni_id bigint NOT NULL
);


ALTER TABLE public.libro_recensioni OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 34846)
-- Name: libro_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.libro_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.libro_seq OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 34887)
-- Name: recensione; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.recensione (
    voto integer NOT NULL,
    id bigint NOT NULL,
    libro_id bigint,
    utente_id bigint,
    testo character varying(2000),
    titolo character varying(255),
    CONSTRAINT recensione_voto_check CHECK (((voto <= 5) AND (voto >= 1)))
);


ALTER TABLE public.recensione OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 34847)
-- Name: recensione_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.recensione_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.recensione_seq OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 34897)
-- Name: utente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.utente (
    id bigint NOT NULL,
    cognome character varying(255),
    nome character varying(255)
);


ALTER TABLE public.utente OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 34848)
-- Name: utente_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.utente_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.utente_seq OWNER TO postgres;

--
-- TOC entry 4914 (class 0 OID 34849)
-- Dependencies: 224
-- Data for Name: amministratore; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.amministratore (id, cognome, nome) FROM stdin;
1	Bianchi	Paolo
\.


--
-- TOC entry 4915 (class 0 OID 34856)
-- Dependencies: 225
-- Data for Name: autore; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.autore (data_morte, data_nascita, id, cognome, nazionalita, nome) FROM stdin;
1616-04-23	1564-04-23	1	Shakespeare	inglese	William
1970-06-09	1812-02-07	2	Dickens	inglese	Charles
\.


--
-- TOC entry 4916 (class 0 OID 34863)
-- Dependencies: 226
-- Data for Name: credentials; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.credentials (amministratore_id, id, utente_id, password, role, username) FROM stdin;
\N	1	1	$2a$10$eBFuAufj.oJvOnV2dmpt1.71P9.FAlVkFRpeYiOm5XLcoaYj/0J82	USER	MarioRossi
1	2	\N	$2a$10$1bKESriU1DaMdCe5yLVsmuDNz3KHcrpzMXknV8D/eq4tTYoXx5wPK	ADMIN	PaoloBianchi
\N	52	2	$2a$10$NtgC7j5ctyGlWAEGhi.bauIQcZBiJ04COWdj7Mdyw8X5ZaWoSeHBW	USER	CarloVerdi
\.


--
-- TOC entry 4917 (class 0 OID 34876)
-- Dependencies: 227
-- Data for Name: libro; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.libro (anno, id, titolo) FROM stdin;
1611	1	la tempesta
1836	2	il circolo pickwick
1840	52	le avventure di oliver twist
\.


--
-- TOC entry 4918 (class 0 OID 34881)
-- Dependencies: 228
-- Data for Name: libro_autori; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.libro_autori (autori_id, libri_id) FROM stdin;
1	1
2	2
2	52
\.


--
-- TOC entry 4919 (class 0 OID 34884)
-- Dependencies: 229
-- Data for Name: libro_immagini; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.libro_immagini (libro_id, immagine_url) FROM stdin;
\.


--
-- TOC entry 4907 (class 0 OID 34775)
-- Dependencies: 217
-- Data for Name: libro_recensioni; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.libro_recensioni (libro_id, recensioni_id) FROM stdin;
\.


--
-- TOC entry 4920 (class 0 OID 34887)
-- Dependencies: 230
-- Data for Name: recensione; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.recensione (voto, id, libro_id, utente_id, testo, titolo) FROM stdin;
2	2	2	1	l'ho trovato monotono e ripetitivo... non è il libro che fa per me.	brutto
4	203	1	1	molto bello, mi è piaciuto molto.	molto bello
2	252	52	2	la trama non mi ha convinto, carino ma abbastanza noioso, non lo leggerei più...	carino
4	253	1	2	libro molto interessante e avvincente, da subito ti coinvolge nel racconto. Ben fatto!	bello
3	254	2	2	l'ho trovato un po' noioso ma i personaggi sono interessanti, non male.	non male
\.


--
-- TOC entry 4921 (class 0 OID 34897)
-- Dependencies: 231
-- Data for Name: utente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.utente (id, cognome, nome) FROM stdin;
1	Rossi	Mario
2	Verdi	Carlo
\.


--
-- TOC entry 4927 (class 0 OID 0)
-- Dependencies: 218
-- Name: amministratore_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.amministratore_seq', 1, true);


--
-- TOC entry 4928 (class 0 OID 0)
-- Dependencies: 219
-- Name: autore_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.autore_seq', 151, true);


--
-- TOC entry 4929 (class 0 OID 0)
-- Dependencies: 220
-- Name: credentials_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.credentials_seq', 101, true);


--
-- TOC entry 4930 (class 0 OID 0)
-- Dependencies: 221
-- Name: libro_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.libro_seq', 101, true);


--
-- TOC entry 4931 (class 0 OID 0)
-- Dependencies: 222
-- Name: recensione_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.recensione_seq', 301, true);


--
-- TOC entry 4932 (class 0 OID 0)
-- Dependencies: 223
-- Name: utente_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.utente_seq', 51, true);


--
-- TOC entry 4736 (class 2606 OID 34855)
-- Name: amministratore amministratore_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.amministratore
    ADD CONSTRAINT amministratore_pkey PRIMARY KEY (id);


--
-- TOC entry 4738 (class 2606 OID 34862)
-- Name: autore autore_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.autore
    ADD CONSTRAINT autore_pkey PRIMARY KEY (id);


--
-- TOC entry 4740 (class 2606 OID 34871)
-- Name: credentials credentials_amministratore_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credentials
    ADD CONSTRAINT credentials_amministratore_id_key UNIQUE (amministratore_id);


--
-- TOC entry 4742 (class 2606 OID 34869)
-- Name: credentials credentials_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credentials
    ADD CONSTRAINT credentials_pkey PRIMARY KEY (id);


--
-- TOC entry 4744 (class 2606 OID 34875)
-- Name: credentials credentials_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credentials
    ADD CONSTRAINT credentials_username_key UNIQUE (username);


--
-- TOC entry 4746 (class 2606 OID 34873)
-- Name: credentials credentials_utente_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credentials
    ADD CONSTRAINT credentials_utente_id_key UNIQUE (utente_id);


--
-- TOC entry 4748 (class 2606 OID 34880)
-- Name: libro libro_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.libro
    ADD CONSTRAINT libro_pkey PRIMARY KEY (id);


--
-- TOC entry 4734 (class 2606 OID 34779)
-- Name: libro_recensioni libro_recensioni_recensioni_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.libro_recensioni
    ADD CONSTRAINT libro_recensioni_recensioni_id_key UNIQUE (recensioni_id);


--
-- TOC entry 4750 (class 2606 OID 34896)
-- Name: recensione recensione_libro_id_utente_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recensione
    ADD CONSTRAINT recensione_libro_id_utente_id_key UNIQUE (libro_id, utente_id);


--
-- TOC entry 4752 (class 2606 OID 34894)
-- Name: recensione recensione_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recensione
    ADD CONSTRAINT recensione_pkey PRIMARY KEY (id);


--
-- TOC entry 4754 (class 2606 OID 34903)
-- Name: utente utente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.utente
    ADD CONSTRAINT utente_pkey PRIMARY KEY (id);


--
-- TOC entry 4755 (class 2606 OID 34904)
-- Name: credentials fk3d21n97wwbuj1nfd8bq4rpu3a; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credentials
    ADD CONSTRAINT fk3d21n97wwbuj1nfd8bq4rpu3a FOREIGN KEY (amministratore_id) REFERENCES public.amministratore(id);


--
-- TOC entry 4756 (class 2606 OID 34909)
-- Name: credentials fk95on1o3appqtcy9gbn8gtdojr; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credentials
    ADD CONSTRAINT fk95on1o3appqtcy9gbn8gtdojr FOREIGN KEY (utente_id) REFERENCES public.utente(id);


--
-- TOC entry 4760 (class 2606 OID 34934)
-- Name: recensione fkb77d6048y1xbjljw1aaew6dt9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recensione
    ADD CONSTRAINT fkb77d6048y1xbjljw1aaew6dt9 FOREIGN KEY (utente_id) REFERENCES public.utente(id);


--
-- TOC entry 4757 (class 2606 OID 34919)
-- Name: libro_autori fkg15jpx2m4xygtx9459ykf6iw8; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.libro_autori
    ADD CONSTRAINT fkg15jpx2m4xygtx9459ykf6iw8 FOREIGN KEY (libri_id) REFERENCES public.libro(id);


--
-- TOC entry 4759 (class 2606 OID 34924)
-- Name: libro_immagini fkh1700kwfu9vyulfuiu14r8c4l; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.libro_immagini
    ADD CONSTRAINT fkh1700kwfu9vyulfuiu14r8c4l FOREIGN KEY (libro_id) REFERENCES public.libro(id);


--
-- TOC entry 4758 (class 2606 OID 34914)
-- Name: libro_autori fknyw1cwifvy7sbbf5gti8apoya; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.libro_autori
    ADD CONSTRAINT fknyw1cwifvy7sbbf5gti8apoya FOREIGN KEY (autori_id) REFERENCES public.autore(id);


--
-- TOC entry 4761 (class 2606 OID 34929)
-- Name: recensione fko6yeeuyqb0kc4jdxbqid79pfj; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recensione
    ADD CONSTRAINT fko6yeeuyqb0kc4jdxbqid79pfj FOREIGN KEY (libro_id) REFERENCES public.libro(id);


-- Completed on 2025-06-08 21:13:46

--
-- PostgreSQL database dump complete
--

