import { getCompanyDetail } from "@/lib/api";
import CompanyHeader from "@/components/company/CompanyHeader";
import FinancialTable from "@/components/company/FinancialTable";
import MetricCards from "@/components/company/MetricCards";
import PriceChart from "@/components/company/PriceChart";

interface CompanyPageProps {
  params: Promise<{ stockCode: string }>;
}

export default async function CompanyPage({ params }: CompanyPageProps) {
  const { stockCode } = await params;
  const data = await getCompanyDetail(stockCode);

  return (
    <main className="px-6 py-10">
      <div className="mx-auto flex max-w-4xl flex-col gap-4">
        <CompanyHeader info={data.info} />
        <PriceChart stockCode={stockCode} />
        <MetricCards metrics={data.metrics.items} />
        <FinancialTable statements={data.financials.statements} />
      </div>
    </main>
  );
}
