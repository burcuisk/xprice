-- Check if the product already exists
INSERT INTO product (id, display_name)
    SELECT '284776f6-1a7c-425b-985f-2161b8cfda1e', 'MacBook Air M2'
    WHERE NOT EXISTS (SELECT 1 FROM product WHERE id = '284776f6-1a7c-425b-985f-2161b8cfda1e');

-- Check if the category_page for TRENDYOL already exists
INSERT INTO category_page (id, third_party_service, url, product_id)
    SELECT
        '8fe02707-0665-4810-a07d-37c22d2efbc1',
        'TRENDYOL',
        'https://public.trendyol.com/discovery-web-searchgw-service/v2/api/filter/sr?wb=101470&attr=168%7C1211145_1186167&lc=103108&q=macbook+air&qt=macbook+air&st=macbook+air&os=1&sst=PRICE_BY_ASC&culture=tr-TR&userGenderId=1&pId=0&scoringAlgorithmId=2&isLegalRequirementConfirmed=false&productStampType=TypeA&fixSlotProductAdsIncluded=true&location=null&searchAbDecider=Suggestion_E%2CMB_B%2CFRA_2%2CMRF_1%2CARR_B%2CBrowsingHistoryCard_B%2CSP_B%2CPastSearches_B%2CSearchWEB_13%2CSuggestionJFYProducts_B%2CSDW_23%2CBSA_D%2CBadgeBoost_A%2CCatTR_B%2CRelevancy_1%2CFilterRelevancy_1%2CListingScoringAlgorithmId_1%2CSmartlisting_65%2CSuggestionBadges_B%2CProductGroupTopPerformer_B%2COpenFilterToggle_2%2CRF_4%2CCS_1%2CRR_2%2CBS_2%2CSuggestionPopularCTR_B&initialSearchText=macbook+air&channelId=1',
        '284776f6-1a7c-425b-985f-2161b8cfda1e'
    WHERE NOT EXISTS (SELECT 1 FROM category_page WHERE third_party_service = 'TRENDYOL' AND product_id = '284776f6-1a7c-425b-985f-2161b8cfda1e');

-- Check if the category_page for HEPSIBURADA already exists
INSERT INTO category_page (id, third_party_service, url, product_id)
    SELECT
        '6c2872d9-8339-45c9-b617-f3f8a9f19c5e',
        'HEPSIBURADA',
        'https://blackgate.hepsiburada.com/moriaapi/api/product-and-facet?filter=MainCategory.Id:98%3Bislemcitipi:Apple%E2%82%AC20M2%3Bislemci:Apple%E2%82%AC20M2%3Bmarkalar:apple&pageType=Search&q=macbook+air&sortType=artanfiyat',
        '284776f6-1a7c-425b-985f-2161b8cfda1e'
    WHERE NOT EXISTS (SELECT 1 FROM category_page WHERE third_party_service = 'HEPSIBURADA' AND product_id = '284776f6-1a7c-425b-985f-2161b8cfda1e');
